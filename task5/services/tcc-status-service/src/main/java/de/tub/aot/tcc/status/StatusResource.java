package de.tub.aot.tcc.status;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/api/status")
@ApplicationScoped
@DenyAll
public class StatusResource {

    @GET
    @Path("/traffic")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "emergency-vehicle", "mayor-vehicle", "other-vehicle", "pedestrian", "traffic-management-center" })
    public TrafficStatus getTrafficStatus(@QueryParam("vehicle") Boolean vehicle) {
        TrafficStatus status = new TrafficStatus();
        status.setState("green");
        status.setTimestamp("2024-01-01T12:00:00Z");
        return status;
    }
}
