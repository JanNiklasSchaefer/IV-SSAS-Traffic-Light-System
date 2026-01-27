package de.tub.aot.locationvalidator;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/location")
@ApplicationScoped
@DenyAll
public class LocationValidator {

    private final LocationMap locations = new LocationMap();

    @POST
    @Path("/vehicle")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "tcc-priority-service-location-role" })
    public LocationValidationResponse validateVehicleLocation(LocationValidationRequest request) {
        // TEMPORARY: Always return valid for testing
        return new LocationValidationResponse(true, "Location validated successfully (test mode)");
    }
}
