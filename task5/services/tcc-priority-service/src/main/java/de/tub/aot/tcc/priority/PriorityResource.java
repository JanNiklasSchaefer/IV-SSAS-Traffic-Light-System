package de.tub.aot.tcc.priority;

import de.tub.aot.locationvalidator.LocationValidationRequest;
import de.tub.aot.locationvalidator.LocationValidationResponse;
import de.tub.aot.tcc.state.StateChangeRequest;
import de.tub.aot.timeservice.TimeValidationRequest;
import de.tub.aot.timeservice.TimeValidationResponse;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("/api/priority")
@ApplicationScoped
@DenyAll
public class PriorityResource {

    private Map<String, RequestStatus> requestStore = new HashMap<>();

    @Inject
    @RestClient
    PriorityTimeClient timeClient;

    @Inject
    @RestClient
    PriorityLocationClient locationClient;

    @Inject
    @RestClient
    PriorityStateControllerClient stateControllerClient;

    @Inject
    @RestClient
    PriorityAuditService auditClient;

    @POST
    @Path("/requests")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "emergency-vehicle", "mayor-vehicle", "traffic-management-center" })
    public PriorityResponse createPriorityRequest(PriorityRequest request) {
        String requestId = UUID.randomUUID().toString();
        String status = "accepted";

        try {
            // 1. Time Service aufrufen - Timestamp validieren
            TimeValidationRequest timeRequest = new TimeValidationRequest(Instant.now().toEpochMilli());
            TimeValidationResponse timeResponse = timeClient.validateTimestamp(timeRequest);
            System.out.println("Time Service Response: " + (timeResponse != null ? "OK" : "NULL"));

            // 2. Location Validator aufrufen - Vehicle Location validieren
            // Beispiel: LocationValidationRequest würde vehicleId und coordinates benötigen
            // Hier nur als Beispiel - in echter Implementierung würde das vom Request
            // kommen
            LocationValidationRequest locationRequest = new LocationValidationRequest(
                    "vehicle-" + requestId,
                    null // Coordinates würden hier gesetzt werden
            );
            try {
                LocationValidationResponse locationResponse = locationClient.validateVehicleLocation(locationRequest);
                System.out.println("Location Validator Response: " + (locationResponse != null ? "OK" : "NULL"));
            } catch (Exception e) {
                System.err.println("Location validation failed (expected if coordinates not set): " + e.getMessage());
            }

            // 3. State Controller aufrufen - State Change anstoßen
            StateChangeRequest stateRequest = new StateChangeRequest();
            stateRequest.setState("priority-request");
            try {
                Response stateResponse = stateControllerClient.changeState(stateRequest);
                System.out.println("State Controller Response: " + stateResponse.getStatus());
            } catch (Exception e) {
                System.err.println("State Controller call failed: " + e.getMessage());
            }

            // 4. Audit Service aufrufen - Event loggen
            try {
                Response auditResponse = auditClient.logEvent("Priority request created: " + requestId);
                System.out.println("Audit Service Response: " + auditResponse.getStatus());
            } catch (Exception e) {
                System.err.println("Audit Service call failed: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Error during internal service calls: " + e.getMessage());
            // Continue anyway - noch keine Logik, nur Routing testen
        }

        RequestStatus requestStatus = new RequestStatus(requestId, "processing", request.getVehicleType());
        requestStore.put(requestId, requestStatus);

        return new PriorityResponse(status, requestId);
    }

    @GET
    @Path("/requests/{request_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "emergency-vehicle", "mayor-vehicle", "traffic-management-center" })
    public RequestStatus getRequestStatus(@PathParam("request_id") String requestId) {
        RequestStatus status = requestStore.get(requestId);

        if (status == null) {
            // Return a default status indicating not found
            // In a real implementation, you might want to throw a NotFoundException
            return new RequestStatus(requestId, "NOT_FOUND", null);
        }

        return status;
    }
}
