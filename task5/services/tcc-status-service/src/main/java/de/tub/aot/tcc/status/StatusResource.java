package de.tub.aot.tcc.status;

import de.tub.aot.common.models.TrafficStatus;
import de.tub.aot.common.models.TrafficLightId;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import de.tub.aot.timeservice.TimeValidationResponse;
import de.tub.aot.timeservice.TimeValidationRequest;
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

    @Inject
    @RestClient
    TrafficStatusTimeClient timeClient;

    @GET
    @Path("/traffic")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "emergency-vehicle", "mayor-vehicle", "other-vehicle", "pedestrian", "traffic-management-center", "priority-service" })
    public TrafficStatus getTrafficStatus(@QueryParam("traffic-light-id") UUID trafficLightId) {
        // TODO: Implementierung - intern TrafficLightDeviceService aufrufen
        // TODO: Neue Datenstrukturen verwenden (TrafficLightId, lightStates)
        // TODO: Status einer Kreuzung mit allen Ampeln zurückgeben

        try{
            TrafficStatus status = lightClient.getTrafficState(trafficLightId);
            System.out.println("response:" + status);
            return status;
        }
        catch (Exception e) {
            System.err.println("Call failed: " + e.getMessage());
        }

        return new TrafficStatus(null, Instant.now(), "yellow");
    }

    @GET
    @Path("/traffic-lights")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "emergency-vehicle", "mayor-vehicle", "other-vehicle", "pedestrian", "traffic-management-center"})
    public ArrayList<TrafficLightId> getTrafficLights() {
        ArrayList<TrafficLightId> lights = lightClient.getLights();

        return lights;
    }
}
