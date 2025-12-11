package de.tub.aot.locationvalidator;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/location")
@ApplicationScoped
public class LocationValidator {

    private final LocationMap locations = new LocationMap();

    @POST
    @Path("/vehicle")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LocationValidationResponse validateVehicleLocation(LocationValidationRequest request) {
        boolean isValid = locations.validateLocation(request.getVehicleId(), request.getCoordinates());
        
        if (isValid) {
            return new LocationValidationResponse(true, "Location validated successfully");
        } else {
            return new LocationValidationResponse(false, "Invalid location");
        }
    }
}

