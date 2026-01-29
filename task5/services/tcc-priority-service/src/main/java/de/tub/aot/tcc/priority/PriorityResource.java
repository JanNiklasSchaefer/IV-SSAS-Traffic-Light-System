package de.tub.aot.tcc.priority;

import de.tub.aot.locationvalidator.LocationValidationRequest;
import de.tub.aot.locationvalidator.LocationValidationResponse;
import de.tub.aot.tcc.state.StateChangeRequest;
import de.tub.aot.timeservice.TimeValidationRequest;
import de.tub.aot.timeservice.TimeValidationResponse;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.UUID;

@Path("/api/priority")
@ApplicationScoped
@DenyAll
public class PriorityResource {

    private static final Logger LOG = Logger.getLogger(PriorityResource.class);

    private Map<String, RequestStatus> requestStore = new HashMap<>();
    private Map<String, String> activeRequestsByVehicle = new HashMap<>();

    // Priority Queue and Comparator
    private PriorityQueue<QueuedRequest> requestQueue;

    @Inject
    @RestClient
    PriorityTimeClient timeClient;

    @Inject
    @RestClient
    PriorityLocationClient locationClient;

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

        QueuedRequest(String requestId, PriorityRequest request) {
            this.requestId = requestId;
            this.request = request;
            this.timestamp = System.currentTimeMillis();
        }
    }

    @POST
    @Path("/requests")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "emergency-vehicle", "mayor-vehicle", "traffic-management-center" })
    public PriorityResponse createPriorityRequest(PriorityRequest request) {
        String vehicleId = request.getVehicleId();

        if (vehicleId != null && activeRequestsByVehicle.containsKey(vehicleId)) {
            return new PriorityResponse("denied", "already_active_request");
        }

        String requestId = UUID.randomUUID().toString();

        if (vehicleId != null) {
            activeRequestsByVehicle.put(vehicleId, requestId);
        }

        LOG.info("=== Priority Request queued: " + requestId + " ===");

        requestQueue.add(new QueuedRequest(requestId, request));
        requestStore.put(requestId, new RequestStatus(requestId, "queued", request.getVehicleType()));

        processNextRequest();

        return new PriorityResponse("queued", requestId);
    }

    private synchronized void processNextRequest() {
        if (requestQueue.isEmpty())
            return;

        // Process head of queue
        QueuedRequest queuedReq = requestQueue.poll();
        String requestId = queuedReq.requestId;
        PriorityRequest request = queuedReq.request;

        LOG.info("=== Processing request: " + requestId + " ===");
        requestStore.put(requestId, new RequestStatus(requestId, "processing", request.getVehicleType()));

        // 1. Time Service
        LOG.info("Calling Time Service...");
        try {
            TimeValidationRequest timeRequest = new TimeValidationRequest(Instant.now().toEpochMilli());
            TimeValidationResponse timeResponse = timeClient.validateTimestamp(timeRequest);
            LOG.info("Time Service Response: " + (timeResponse != null ? "OK" : "NULL"));
        } catch (Exception e) {
            LOG.warn("Time Service call failed: " + e.getMessage());
        }

        // 2. Location Validator
        LOG.info("Calling Location Validator...");
        try {
            LocationValidationRequest locationRequest = new LocationValidationRequest(
                    "vehicle-" + requestId,
                    null);
            LocationValidationResponse locationResponse = locationClient.validateVehicleLocation(locationRequest);
            LOG.info("Location Validator Response: " + (locationResponse != null ? "OK" : "NULL"));
        } catch (Exception e) {
            LOG.warn("Location validation failed: " + e.getMessage());
        }

        // 3. State Controller
        LOG.info("Calling State Controller...");
        try {
            StateChangeRequest stateRequest = new StateChangeRequest();
            stateRequest.setState("priority-request");
            Response stateResponse = stateControllerClient.changeState(stateRequest);
            LOG.info("State Controller Response: " + stateResponse.getStatus());
        } catch (Exception e) {
            LOG.warn("State Controller call failed: " + e.getMessage());
        }

        // 4. Audit Service
        LOG.info("Calling Audit Service...");
        try {
            Response auditResponse = auditClient.logEvent("Priority request processed: " + requestId);
            LOG.info("Audit Service Response: " + auditResponse.getStatus());
        } catch (Exception e) {
            LOG.warn("Audit Service call failed: " + e.getMessage());
        }

        LOG.info("=== Priority Request processing completed: " + requestId + " ===");

        requestStore.put(requestId, new RequestStatus(requestId, "accepted", request.getVehicleType()));

        if (request.getVehicleId() != null) {
            activeRequestsByVehicle.remove(request.getVehicleId());
        }

        // Recursively process next if any?
        // For now, let's keep it simple. If we want to drain the queue, we loop or call
        // recursively.
        // But the user pattern implies one-by-one triggering or async.
        // Given current sync implementation, one call processes one item.
        // If multiple come in parallel, they trigger multiple processNextRequest.
        // With 'synchronized', they serialize.
        // But if 5 items are in queue, and 1 finishes, who triggers the next?
        // We should try to process more if available.
        if (!requestQueue.isEmpty()) {
            processNextRequest();
        }
    }

    @GET
    @Path("/requests/{request_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "emergency-vehicle", "mayor-vehicle", "traffic-management-center" })
    public RequestStatus getRequestStatus(@PathParam("request_id") String requestId) {
        RequestStatus status = requestStore.get(requestId);

        if (status == null) {
            return new RequestStatus(requestId, "NOT_FOUND", null);
        }

        return status;
    }
}
