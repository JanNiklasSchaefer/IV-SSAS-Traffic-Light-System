package de.tub.aot.timeservice;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.annotation.security.DenyAll;

@Path("/api/time")
@ApplicationScoped
@DenyAll
public class TimeService {

    private final GlobalTimer globalTimer = new GlobalTimer();

    @POST
    @Path("/validate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"tcc-status-service-time-service","tcc-priority-service-time-service"})
    public TimeValidationResponse validateTimestamp(TimeValidationRequest request) {
        boolean isValid = globalTimer.validateTime(request.getTimestamp());
        
        if (isValid) {
            return new TimeValidationResponse(true, "Timestamp validated successfully");
        } else {
            return new TimeValidationResponse(false, "Timestamp is outside valid range");
        }
    }
}

