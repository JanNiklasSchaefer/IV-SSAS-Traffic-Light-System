package de.tub.aot.tcc.priority;

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
import de.tub.aot.common.models.TrafficStatus;
import de.tub.aot.common.models.PriorityRequest;
import de.tub.aot.common.models.PriorityResponse;

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

    private Map<String, QueuedRequest> requestStore = new HashMap<>();
    private Map<String, String> activeRequestsByVehicle = new HashMap<>();

    // Priority Queue and Comparator
    private PriorityQueue<QueuedRequest> requestQueue;

    @Inject
    @RestClient
    PriorityTimeClient timeClient;

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
    @RolesAllowed({ "emergency-vehicle", "mayor-vehicle"})
    public PriorityResponse createPriorityRequest(PriorityRequest currentRequest) {
        String vehicleId = currentRequest.getVehicleId();

        if(vehicleId == null){
            return new PriorityResponse("denied", "missing vehicleId");
        }

        if(currentRequest.getVehicleType() == null){
            return new PriorityResponse("denied", "missing vehicleType");
        }

        if (vehicleId != null && activeRequestsByVehicle.containsKey(vehicleId)) {
            return new PriorityResponse("denied", "Request with ID: " + activeRequestsByVehicle.get(vehicleId) + " already exists.");
        }

        String requestId = UUID.randomUUID().toString();

        QueuedRequest queueRequest = new QueuedRequest(requestId, currentRequest, "queued");
        activeRequestsByVehicle.put(vehicleId, requestId);
        requestStore.put(requestId, queueRequest);

        LOG.info("=== Priority Request queued: " + requestId + " ===");

        requestQueue.add(queueRequest);

        QueuedRequest topRequest = requestQueue.peek();

        TrafficStatus trafficStatus = statusClient.getTrafficState(currentRequest.getTrafficLightId());
         
        if(trafficStatus.getState().equals("yellow")){
            return new PriorityResponse("queued", "Traffic is Currently Yellow, thus Request is queued. Changing now can lead to unsafe traffic States.");
        }
        
        if(trafficStatus.getState().equals("green")){
            return new PriorityResponse("accepted", "Traffic Light was already Green. If Vehicles are blocking you in front, only start a PriorityRequest when noone is in front of you.");
        }
        
        
        if(topRequest.request.getVehicleId().equals(currentRequest.getVehicleId())){
            stateControllerClient.changeState();
            //Remove Entries from prio queue and active maps for priority calls 
            requestQueue.poll();
            requestStore.remove(requestId);
            activeRequestsByVehicle.remove(currentRequest.getVehicleId());
            return new PriorityResponse("accepted", requestId);
        }

        return new PriorityResponse("queued", requestId);
    }


    @GET
    @Path("/requests/{request_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "emergency-vehicle", "mayor-vehicle"})
    public PriorityResponse getRequestStatus(@PathParam("request_id") String requestId) {

        //TODO Add check if current state = state of priority request and adjust accordingly 

        QueuedRequest currentQueuedRequest = requestStore.get(requestId);

        if (currentQueuedRequest == null) {
            return new PriorityResponse("NOT_FOUND", requestId);
        }

        TrafficStatus trafficStatus = statusClient.getTrafficState(currentQueuedRequest.request.getTrafficLightId());

        if(trafficStatus.getState().equals("yellow")){
            return new PriorityResponse("queued", "Traffic is Currently Yellow, thus Request is queued. Changing now can lead to unsafe traffic States.");
        }
        
        if(trafficStatus.getState().equals("green")){
            requestQueue.poll();
            requestStore.remove(requestId);
            activeRequestsByVehicle.remove(currentQueuedRequest.request.getVehicleId());
            return new PriorityResponse("accepted", "Traffic Light is already Green. PriorityRequest with ID: " + requestId + " was deleted. If you can't pass due to blocking traffic, send another PriorityRequest once you are at the front.");
        }
        

        QueuedRequest bestRequest = requestQueue.peek();

        if(bestRequest.requestId.equals(currentQueuedRequest.requestId)){
            stateControllerClient.changeState();
            //Remove Entries from prio queue and active maps for priority calls 
            requestQueue.poll();
            requestStore.remove(requestId);
            activeRequestsByVehicle.remove(currentQueuedRequest.request.getVehicleId());
            return new PriorityResponse("accepted", "Request with Request ID: " + requestId + " accepted. Request is now deleted.");
        }
        

        return new PriorityResponse("queued", requestId);
    }
}
