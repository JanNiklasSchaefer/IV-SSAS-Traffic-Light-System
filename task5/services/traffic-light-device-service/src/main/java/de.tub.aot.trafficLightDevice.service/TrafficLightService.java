package de.tub.aot.trafficLightDevice.service;

import de.tub.aot.common.models.TrafficStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Consumes;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;

@Path("/api/device")
@ApplicationScoped
@DenyAll
public class TrafficLightService {

    @GET
    @Path("/traffic-state")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"tcc-state-controller-light-service","tcc-status-service-light-service"})
    public TrafficStatus getTrafficState(@QueryParam("vehicle") Boolean vehicle) {
        TrafficStatus status = new TrafficStatus();
        if(vehicle){
            status.setState("green");
            status.setTimestamp("2024-01-01T12:00:00Z");
        }
        else{
            status.setState("red");
            status.setTimestamp("2024-01-01T12:00:00Z");
        }
        return status;
    }

    @POST
    @Path("/change-state")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "tcc-state-controller-light-service", "tcc-status-service-light-service" })
    public Response changeState(@QueryParam("state") String currentState) {

        TrafficStatus newStatus = new TrafficStatus();
        if ("green".equals(currentState)) {
            newStatus.setState("green");
        } else
            newStatus.setState("red");

        newStatus.setTimestamp("2024-01-02T12:00:00Z");
        return Response.ok(newStatus).build();
    }

}
