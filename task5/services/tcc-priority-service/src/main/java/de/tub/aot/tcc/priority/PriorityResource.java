package de.tub.aot.tcc.priority;

import de.tub.aot.tcc.state.StateChangeRequest;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import de.tub.aot.common.models.TrafficStatus;
import de.tub.aot.common.models.PriorityRequest;
import de.tub.aot.common.models.PriorityResponse;

import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.UUID;

@Path("/api/priority")
@ApplicationScoped
@DenyAll
public class PriorityResource {

    private static final Logger LOG = Logger.getLogger(PriorityResource.class);

    // Allowed vehicle types
    private static final Set<String> ALLOWED_VEHICLE_TYPES = Set.of("emergency-vehicle", "mayor-vehicle", "other");

    private Map<String, QueuedRequest> requestStore = new HashMap<>();
    private Map<String, String> activeRequestsByVehicle = new HashMap<>();

    // Priority Queue and Comparator
    private PriorityQueue<QueuedRequest> requestQueue;

    @Inject
    @RestClient
    PriorityRequestStatusClient statusClient;

    @Inject
    @RestClient
    PriorityStateControllerClient stateControllerClient;

    @Inject
    @RestClient
    PriorityAuditService auditClient;

    public PriorityResource() {
        Comparator<QueuedRequest> comparator = (r1, r2) -> {
            int p1 = getPriority(r1.request.getVehicleType());
            int p2 = getPriority(r2.request.getVehicleType());
            // Lower value means higher priority (1 < 2 < 3)
            if (p1 != p2)
                return Integer.compare(p1, p2);
            // FIFO for same priority
            return Long.compare(r1.timestamp, r2.timestamp);
        };
        this.requestQueue = new PriorityQueue<>(comparator);
    }

    private boolean isValidVehicleId(String vehicleId) {
        return vehicleId != null && !vehicleId.trim().isEmpty();
    }

    private boolean isValidVehicleType(String vehicleType) {
        return vehicleType != null && !vehicleType.trim().isEmpty() && ALLOWED_VEHICLE_TYPES.contains(vehicleType);
    }

    private boolean isValidTrafficLightId(UUID trafficLightId) {
        return trafficLightId != null;
    }

    private boolean isValidRequestId(String requestId) {
        if (requestId == null || requestId.trim().isEmpty()) {
            return false;
        }
        try {
            UUID.fromString(requestId);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private int getPriority(String type) {
        if ("emergency-vehicle".equals(type))
            return 1;
        if ("mayor-vehicle".equals(type))
            return 2;
        return 3; // Other
    }

    private static class QueuedRequest {
        String requestId;
        PriorityRequest request;
        long timestamp;
        String requestState;

        QueuedRequest(String requestId, PriorityRequest request, String requestState) {
            this.requestId = requestId;
            this.request = request;
            this.requestState = requestState;
            this.timestamp = System.currentTimeMillis();
        }
    }

    @POST
    @Path("/requests")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "emergency-vehicle", "mayor-vehicle" })
    public PriorityResponse createPriorityRequest(PriorityRequest currentRequest) {
        // Input validation
        String requestId = UUID.randomUUID().toString();

        if (currentRequest == null ||
                (currentRequest.getVehicleId() == null &&
                        currentRequest.getVehicleType() == null &&
                        currentRequest.getTrafficLightId() == null)) {
            return new PriorityResponse("denied", "Request body is required or incomplete");
        }

        String vehicleId = currentRequest.getVehicleId();
        if (!isValidVehicleId(vehicleId)) {
            return new PriorityResponse("denied", "Invalid or missing vehicleId");
        }

        String vehicleType = currentRequest.getVehicleType();
        if (!isValidVehicleType(vehicleType)) {
            return new PriorityResponse("denied",
                    "Invalid vehicleType. Allowed values: emergency-vehicle, mayor-vehicle, other");
        }

        UUID trafficLightId = currentRequest.getTrafficLightId();
        if (!isValidTrafficLightId(trafficLightId)) {
            return new PriorityResponse("denied", "Invalid or missing trafficLightId");
        }

        if (this.activeRequestsByVehicle.containsKey(vehicleId)) {
            return new PriorityResponse("denied",
                    "Request with ID: " + this.activeRequestsByVehicle.get(vehicleId) + " already exists.");
        }

        QueuedRequest queueRequest = new QueuedRequest(requestId, currentRequest, "queued");
        this.activeRequestsByVehicle.put(vehicleId, requestId);
        this.requestStore.put(requestId, queueRequest);

        LOG.info("=== Priority Request queued: " + requestId + " ===");

        this.requestQueue.add(queueRequest);

        QueuedRequest topRequest = requestQueue.peek();

        TrafficStatus trafficStatus = statusClient.getTrafficState(currentRequest.getTrafficLightId());

        if (trafficStatus.getState().equals("yellow")) {
            return new PriorityResponse("queued",
                    requestId,
                    "Traffic is Currently Yellow, thus Request is queued. Changing now can lead to unsafe traffic States.");
        }

        if (trafficStatus.getState().equals("green")) {
            this.requestQueue.poll();
            this.requestStore.remove(requestId);
            this.activeRequestsByVehicle.remove(currentRequest.getVehicleId());
            return new PriorityResponse("accepted",
                    requestId,
                    "Traffic Light was already Green. If Vehicles are blocking you in front, only start a PriorityRequest when nobody is in front of you.");
        }

        if (topRequest.request.getVehicleId().equals(currentRequest.getVehicleId())) {
                //When we call the state do some fancy body parsing so we get the actual relevant http return
                Response r = null;
                String body = null;

                try {
                    r = stateControllerClient.changeState();

                    // Always log status first
                    int status = r.getStatus();

                    // Read body safely (may be empty)
                    try {
                        body = r.readEntity(String.class);
                    } catch (Exception ignored) {
                        body = "<unreadable>";
                    }

                    LOG.infof("changeState() -> status=%d, body=%s", status, body);

                    // Treat non-2xx as failure
                    if (status / 100 != 2) {
                        return new PriorityResponse(
                            "queued",
                            requestId,
                            "State controller call failed: HTTP " + status + " " + (body != null ? body : "")
                        );
                    }
                    else{ 
                        // Succesfull State Change : Remove Entries from prio queue and active maps for priority calls
                        this.requestQueue.poll();
                        this.requestStore.remove(requestId);
                        this.activeRequestsByVehicle.remove(currentRequest.getVehicleId());
                        return new PriorityResponse("accepted", requestId);
                    }
                } catch (Exception e) {
                    // If the client throws (timeouts, connection errors, etc.)
                    LOG.error("changeState() call threw exception", e);
                    return new PriorityResponse("denied", "State controller unreachable: " + e.getMessage());
                } finally {
                    if (r != null) {
                        try { r.close(); } catch (Exception ignored) {}
                    }
                }
        }

        return new PriorityResponse("queued", requestId);
    }

    @GET
    @Path("/requests/{request_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "emergency-vehicle", "mayor-vehicle" })
    public PriorityResponse getRequestStatus(@PathParam("request_id") String requestId) {
        // Input validation
        if (!isValidRequestId(requestId)) {
            return new PriorityResponse("denied", "Invalid requestId format (must be valid UUID)");
        }

        // TODO Add check if current state = state of priority request and adjust
        // accordingly

        QueuedRequest currentQueuedRequest = this.requestStore.get(requestId);

        if (currentQueuedRequest == null) {
            return new PriorityResponse("NOT_FOUND", requestId);
        }

        TrafficStatus trafficStatus = statusClient.getTrafficState(currentQueuedRequest.request.getTrafficLightId());

        if (trafficStatus.getState().equals("yellow")) {
            return new PriorityResponse("queued",
                    requestId,
                    "Traffic is Currently Yellow, thus Request is queued. Changing now can lead to unsafe traffic States.");
        }

        if (trafficStatus.getState().equals("green")) {
            this.requestQueue.poll();
            this.requestStore.remove(requestId);
            this.activeRequestsByVehicle.remove(currentQueuedRequest.request.getVehicleId());
            return new PriorityResponse("accepted", "Traffic Light is already Green. PriorityRequest with ID: "
                    + requestId
                    + " was deleted. If you can't pass due to blocking traffic, send another PriorityRequest once you are at the front.");
        }

        QueuedRequest bestRequest = this.requestQueue.peek();

        if (bestRequest.requestId.equals(currentQueuedRequest.requestId)) {
            Response r = null;
            String body = null;

            try {
                r = stateControllerClient.changeState();

                // Always log status first
                int status = r.getStatus();

                // Read body safely (may be empty)
                try {
                    body = r.readEntity(String.class);
                } catch (Exception ignored) {
                    body = "<unreadable>";
                }

                LOG.infof("changeState() -> status=%d, body=%s", status, body);

                // Treat non-2xx as failure
                if (status / 100 != 2) {
                    return new PriorityResponse(
                        "queued",
                        "State controller call failed: HTTP " + status + " " + (body != null ? body : "")
                    );
                }
                else{
                    // Remove Entries from prio queue and active maps for priority calls
                    this.requestQueue.poll();
                    this.requestStore.remove(requestId);
                    this.activeRequestsByVehicle.remove(currentQueuedRequest.request.getVehicleId());
                    return new PriorityResponse("accepted",
                            "Request with Request ID: " + requestId + " accepted. Request is now deleted.");
                        }
            } catch (Exception e) {
                // If the client throws (timeouts, connection errors, etc.)
                LOG.error("changeState() call threw exception", e);
                return new PriorityResponse("denied", "State controller unreachable: " + e.getMessage());
            } finally {
                if (r != null) {
                    try { r.close(); } catch (Exception ignored) {}
                }
            }

        }

        return new PriorityResponse("queued", requestId);
    }
}


