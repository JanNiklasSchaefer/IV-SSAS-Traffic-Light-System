package de.tub.aot.tcc.status;

import de.tub.aot.common.models.TrafficStatus;
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
import de.tub.aot.trafficLightDevice.service.TrafficStatus;
import de.tub.aot.timeservice.TimeValidationResponse;
import de.tub.aot.timeservice.TimeValidationRequest;


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
    @RolesAllowed({ "emergency-vehicle", "mayor-vehicle", "other-vehicle", "pedestrian", "traffic-management-center" })
    public TrafficStatus getTrafficStatus(@QueryParam("vehicle") Boolean vehicle) {
        // TODO: Implementierung - intern TrafficLightDeviceService aufrufen
        // TODO: Neue Datenstrukturen verwenden (TrafficLightId, lightStates)
        // TODO: Status einer Kreuzung mit allen Ampeln zurückgeben

        TrafficStatus status = new TrafficStatus();
        try{
            status = statusClient.getState(vehicle);
            System.out.println("response:" + status);
            return status;
        }
        catch (Exception e) {
            System.err.println("Call failed: " + e.getMessage());
        }
        status.setState("yellow");
        status.setTimestamp("2024-01-01T12:00:00Z");
        return status;
    }
}
