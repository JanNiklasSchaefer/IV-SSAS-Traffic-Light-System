package de.tub.aot.timeservice;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/time")
@ApplicationScoped
public class TimeService {

    private final GlobalTimer globalTimer = new GlobalTimer();

    @POST
    @Path("/validate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TimeValidationResponse validateTimestamp(TimeValidationRequest request) {
        boolean isValid = globalTimer.validateTime(request.getTimestamp());
        
        if (isValid) {
            return new TimeValidationResponse(true, "Timestamp validated successfully");
        } else {
            return new TimeValidationResponse(false, "Timestamp is outside valid range");
        }
    }
}

