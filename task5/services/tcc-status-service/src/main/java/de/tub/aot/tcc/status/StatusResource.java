package de.tub.aot.tcc.status;

import de.tub.aot.common.models.TrafficStatus;
import de.tub.aot.common.models.TrafficLightId;
import de.tub.aot.common.models.PriorityResponse;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.ArrayList;
import java.time.Instant;
import java.util.UUID;

@Path("/api/status")
@ApplicationScoped
@DenyAll
public class StatusResource {

    @Inject
    @RestClient
    TrafficStatusLightClient lightClient;

    @GET
    @Path("/traffic")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "emergency-vehicle", "mayor-vehicle", "other-vehicle", "pedestrian", "traffic-management-center",
            "priority-service" })
    public Response getTrafficStatus(@QueryParam("traffic-light-id") String trafficLightIdStr) {

        if (trafficLightIdStr == null || trafficLightIdStr.trim().isEmpty()) {
            return Response.ok(new PriorityResponse("denied", "Missing or empty traffic-light-id parameter")).build();
        }

        UUID trafficLightId;
        try {
            trafficLightId = UUID.fromString(trafficLightIdStr);
        } catch (IllegalArgumentException e) {
            return Response.ok(new PriorityResponse("denied", "Invalid traffic-light-id format (must be valid UUID)"))
                    .build();
        }

        try {
            TrafficStatus status = lightClient.getTrafficState(trafficLightId);
            System.out.println("response:" + status);

            if (status == null) {
                return Response.ok(new PriorityResponse("not_found", "Traffic light not found")).build();
            }

            return Response.ok(status).build();
        } catch (Exception e) {
            System.err.println("Call failed: " + e.getMessage());
            return Response.ok(new PriorityResponse("error", "Internal server error")).build();
        }
    }

    @GET
    @Path("/traffic-lights")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "emergency-vehicle", "mayor-vehicle", "other-vehicle", "pedestrian", "traffic-management-center" })
    public ArrayList<TrafficLightId> getTrafficLights() {
        ArrayList<TrafficLightId> lights = lightClient.getLights();

        return lights;
    }
}
