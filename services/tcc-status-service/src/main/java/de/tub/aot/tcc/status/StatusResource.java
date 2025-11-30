package de.tub.aot.tcc.status;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/status")
@ApplicationScoped
public class StatusResource {

    @GET
    @Path("/traffic")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrafficStatus(@QueryParam("vehicle") Boolean vehicle) {
        TrafficStatus status = new TrafficStatus();
        status.setState("green");
        status.setTimestamp("2024-01-01T12:00:00Z");
        return Response.ok(status).build();
    }
}
