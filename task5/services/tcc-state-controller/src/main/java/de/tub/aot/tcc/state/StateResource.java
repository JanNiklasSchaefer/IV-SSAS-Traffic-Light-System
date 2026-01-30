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
import jakarta.ws.rs.QueryParam;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.UUID;

import de.tub.aot.common.models.TrafficLightId;
import de.tub.aot.common.models.TrafficStatus;

import java.time.Instant;

import jakarta.ws.rs.WebApplicationException;

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
    @Path("/management/state")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "traffic-management-center" })
    public ArrayList<TrafficStatus> getIntersectionState() {
        ArrayList<TrafficStatus> intersectionStatus = new ArrayList<TrafficStatus>();
        ArrayList<TrafficLightId> trafficLightIdArray = new ArrayList<TrafficLightId>();

        //First Query all traffic light ID's
        trafficLightIdArray = tccStateControllerClient.getTrafficLights();

        //Query status for each traffic light
        for(TrafficLightId id : trafficLightIdArray){
            TrafficStatus status = tccStateControllerClient.getTrafficState(id.getUuid());
            intersectionStatus.add(status);
        }

        LOG.info("Calling Audit Service...");
        try {
            Response auditResponse = auditService.logEvent("Get intersection state" + Instant.now().toString());
            LOG.info("Audit Service Response: " + auditResponse.getStatus());
        } catch (Exception e) {
            LOG.warn("Audit Service call failed: " + e.getMessage());
        }
        
        return intersectionStatus;
    }

    @PUT
    @Path("/management/change-state")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "traffic-management-center" })
    public Response setIntersectionState(@QueryParam("traffic-light-id") UUID trafficLightId, String goalState) {
        LOG.info("=== PUT Traffic Light Device State: " + trafficLightId.toString() + " to " + goalState + " ===");

        // 1. Intern TrafficLightDeviceService aufrufen - State ändern with Debug Functionality
        LOG.info("Calling Traffic Light Device Service...");
        try {
            Response deviceResponse = tccStateControllerClient.managementChangeState(trafficLightId, goalState);

            String body = deviceResponse.hasEntity() ? deviceResponse.readEntity(String.class) : "<no body>";
            LOG.infof("Device response: status=%d body=%s", deviceResponse.getStatus(), body);

            if (deviceResponse.getStatus() >= 400) {
                return Response.status(deviceResponse.getStatus())
                        .entity(body)
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            }

        } catch (WebApplicationException wae) {
            Response r = wae.getResponse();
            String body = (r != null && r.hasEntity()) ? r.readEntity(String.class) : "<no body>";
            int status = (r != null) ? r.getStatus() : 502;

            LOG.warnf("Device call failed: status=%d body=%s", status, body);

            return Response.status(status)
                    .entity(body)
                    .type(MediaType.TEXT_PLAIN)
                    .build();

        } catch (Exception e) {
            LOG.warnf("Device call failed (non-HTTP): %s", e.toString());

            return Response.status(502)
                    .entity("Device service call failed: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }


        // 2. Audit Service aufrufen - Event loggen
        LOG.info("Calling Audit Service...");
        try {
            Response auditResponse = auditService.logEvent("Set intersection state to: " + goalState);
            LOG.info("Audit Service Response: " + auditResponse.getStatus());
        } catch (Exception e) {
            LOG.warn("Audit Service call failed: " + e.getMessage());
        }

        return Response.ok("State of Traffic Light: " + trafficLightId.toString() + " set to: " + goalState + "\n").build();
    }

}
