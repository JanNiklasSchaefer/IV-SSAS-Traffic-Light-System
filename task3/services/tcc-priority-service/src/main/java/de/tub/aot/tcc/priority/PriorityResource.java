package de.tub.aot.tcc.priority;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
    public Response createPriorityRequest(PriorityRequest request) {
        String requestId = UUID.randomUUID().toString();
        String status = "accepted";
        
        RequestStatus requestStatus = new RequestStatus(requestId, "processing", request.getVehicleType());
        requestStore.put(requestId, requestStatus);
        
        PriorityResponse response = new PriorityResponse(status, requestId);
        return Response.ok(response).build();
    }
    
    @GET
    @Path("/requests/{request_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRequestStatus(@PathParam("request_id") String requestId) {
        RequestStatus status = requestStore.get(requestId);

        
        
        /*
        if (status == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "NOT_FOUND");
            error.put("message", "Request with ID " + requestId + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
        */
        
        
        return Response.ok(status).build();
    }
}

