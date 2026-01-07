package de.tub.aot.tcc.priority;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("/api/priority")
@ApplicationScoped
public class PriorityResource {
    
    private Map<String, RequestStatus> requestStore = new HashMap<>();
    
    @POST
    @Path("/requests")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PriorityResponse createPriorityRequest(PriorityRequest request) {
        String requestId = UUID.randomUUID().toString();
        String status = "accepted";
        
        RequestStatus requestStatus = new RequestStatus(requestId, "processing", request.getVehicleType());
        requestStore.put(requestId, requestStatus);
        
        return new PriorityResponse(status, requestId);
    }
    
    @GET
    @Path("/requests/{request_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public RequestStatus getRequestStatus(@PathParam("request_id") String requestId) {
        RequestStatus status = requestStore.get(requestId);
        
        if (status == null) {
            // Return a default status indicating not found
            // In a real implementation, you might want to throw a NotFoundException
            return new RequestStatus(requestId, "NOT_FOUND", null);
        }
        
        return status;
    }
}

