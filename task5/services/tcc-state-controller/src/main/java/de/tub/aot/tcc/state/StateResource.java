package de.tub.aot.tcc.state;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.HashMap;
import java.util.Map;

@Path("/api/state")
@ApplicationScoped
@DenyAll
public class StateResource {

    private static final Logger LOG = Logger.getLogger(StateResource.class);

    @Inject
    @RestClient
    TccStateControllerClient tccStateControllerClient;

    @Inject
    @RestClient
    PriorityAuditService auditService;

    // In-memory store for intersection states (in production, use database)
    private Map<String, IntersectionState> intersectionStore = new HashMap<>();

    @POST
    @Path("/change")
    @RolesAllowed({ "tcc-priority-service-controll-service" })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeState(StateChangeRequest request) {
        // Response output = tccStateControllerClient.changeState(request.getState());
        return Response.ok().build();
    }

    @GET
    @Path("/manual/{intersectionId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "traffic-management-center" })
    public IntersectionState getIntersectionState(@PathParam("intersectionId") String intersectionId) {
        LOG.info("=== GET Intersection State: " + intersectionId + " ===");

        try {
            // 1. Intern TrafficLightDeviceService aufrufen - aktuellen State holen
            LOG.info("Calling Traffic Light Device Service...");
            try {
                Response deviceResponse = tccStateControllerClient.changeState(null);
                LOG.info("Traffic Light Device Service Response: " + deviceResponse.getStatus());
            } catch (Exception e) {
                LOG.warn("Traffic Light Device Service call failed: " + e.getMessage());
            }

            // 2. Audit Service aufrufen - Event loggen
            LOG.info("Calling Audit Service...");
            try {
                Response auditResponse = auditService.logEvent("Get intersection state: " + intersectionId);
                LOG.info("Audit Service Response: " + auditResponse.getStatus());
            } catch (Exception e) {
                LOG.warn("Audit Service call failed: " + e.getMessage());
            }

            // 3. State aus Store holen (oder Default zurückgeben)
            IntersectionState state = intersectionStore.get(intersectionId);
            if (state == null) {
                // Default state wenn nicht vorhanden
                state = new IntersectionState(intersectionId, "green", "all", "system", "default");
            }

            LOG.info("=== GET Intersection State completed: " + intersectionId + " ===");
            return state;
        } catch (Exception e) {
            LOG.error("Error getting intersection state: " + e.getMessage(), e);
            // Return default state on error
            return new IntersectionState(intersectionId, "green", "all", "system", "error");
        }
    }

    @PUT
    @Path("/manual/{intersectionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "traffic-management-center" })
    public Response setIntersectionState(@PathParam("intersectionId") String intersectionId, IntersectionState state) {
        LOG.info("=== PUT Intersection State: " + intersectionId + " to " + state.getState() + " ===");

        try {
            // 1. Intern TrafficLightDeviceService aufrufen - State ändern
            LOG.info("Calling Traffic Light Device Service...");
            try {
                Response deviceResponse = tccStateControllerClient.changeState(state.getState());
                LOG.info("Traffic Light Device Service Response: " + deviceResponse.getStatus());
            } catch (Exception e) {
                LOG.warn("Traffic Light Device Service call failed: " + e.getMessage());
            }

            // 2. Audit Service aufrufen - Event loggen
            LOG.info("Calling Audit Service...");
            try {
                Response auditResponse = auditService
                        .logEvent("Set intersection state: " + intersectionId + " to " + state.getState());
                LOG.info("Audit Service Response: " + auditResponse.getStatus());
            } catch (Exception e) {
                LOG.warn("Audit Service call failed: " + e.getMessage());
            }

            // 3. State im Store speichern
            state.setIntersectionId(intersectionId);
            state.setTimestamp(java.time.Instant.now().toString());
            intersectionStore.put(intersectionId, state);

            LOG.info("=== PUT Intersection State completed: " + intersectionId + " ===");
            return Response.ok(state).build();
        } catch (Exception e) {
            LOG.error("Error setting intersection state: " + e.getMessage(), e);
            return Response.serverError().entity("Error: " + e.getMessage()).build();
        }
    }

}
