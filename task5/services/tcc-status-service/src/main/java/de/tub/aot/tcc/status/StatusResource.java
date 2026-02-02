package de.tub.aot.tcc.status;

import de.tub.aot.common.models.TrafficStatus;
import de.tub.aot.common.models.TrafficLightId;
import de.tub.aot.common.models.PriorityResponse;
import de.tub.aot.common.models.AuditObject;

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
import org.jboss.logging.Logger;


@Path("/api/status")
@ApplicationScoped
@DenyAll
public class StatusResource {

    @Inject
    @RestClient
    TrafficStatusLightClient lightClient;

    @Inject
    @RestClient 
    TrafficStatusAuditClient auditClient;

    private static final Logger LOG = Logger.getLogger(StatusResource.class);


    @GET
    @Path("/traffic")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "emergency-vehicle", "mayor-vehicle", "other-vehicle", "pedestrian", "traffic-management-center",
            "priority-service" })
    public Response getTrafficStatus(@QueryParam("traffic-light-id") String trafficLightIdStr) {

        if (trafficLightIdStr == null || trafficLightIdStr.trim().isEmpty()) {
            try {
                Response auditResponse = auditClient.logEvent(new AuditObject("tcc-status-service", "Status Request denied missing traffic light id"));
                LOG.info("Audit Service Response: " + auditResponse.getStatus());
            } catch (Exception e2) {
                LOG.warn("Audit Service call failed: " + e2.getMessage());
            }
            return Response.ok(new PriorityResponse("denied", "Missing or empty traffic-light-id parameter")).build();
        }

        UUID trafficLightId;
        try {
            trafficLightId = UUID.fromString(trafficLightIdStr);
        } catch (IllegalArgumentException e) {
            try {
                Response auditResponse = auditClient.logEvent(new AuditObject("tcc-status-service", "Status Request denied malformed traffic light id"));
                LOG.info("Audit Service Response: " + auditResponse.getStatus());
            } catch (Exception e2) {
                LOG.warn("Audit Service call failed: " + e2.getMessage());
            }
            return Response.ok(new PriorityResponse("denied", "Invalid traffic-light-id format (must be valid UUID)"))
                    .build();
        }

        try {
            TrafficStatus status = lightClient.getTrafficState(trafficLightId);
            System.out.println("response:" + status);

            if (status == null) {
                try {
                    Response auditResponse = auditClient.logEvent(new AuditObject("tcc-status-service", "Status Request denied traffic light not found"));
                    LOG.info("Audit Service Response: " + auditResponse.getStatus());
                } catch (Exception e2) {
                    LOG.warn("Audit Service call failed: " + e2.getMessage());
                }
                return Response.ok(new PriorityResponse("not_found", "Traffic light not found")).build();
            }
            try {
                Response auditResponse = auditClient.logEvent(new AuditObject("tcc-status-service", "Status Request accepted"));
                LOG.info("Audit Service Response: " + auditResponse.getStatus());
            } catch (Exception e2) {
                LOG.warn("Audit Service call failed: " + e2.getMessage());
            }
            return Response.ok(status).build();
        } catch (Exception e) {
            System.err.println("Call failed: " + e.getMessage());
            try {
                Response auditResponse = auditClient.logEvent(new AuditObject("tcc-status-service", "Status Request denied internal server error"));
                LOG.info("Audit Service Response: " + auditResponse.getStatus());
            } catch (Exception e2) {
                LOG.warn("Audit Service call failed: " + e2.getMessage());
            }
            return Response.ok(new PriorityResponse("error", "Internal server error")).build();
        }
    }

    @GET
    @Path("/traffic-lights")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "emergency-vehicle", "mayor-vehicle", "other-vehicle", "pedestrian", "traffic-management-center" })
    public ArrayList<TrafficLightId> getTrafficLights() {
        ArrayList<TrafficLightId> lights = lightClient.getLights();
        try {
                Response auditResponse = auditClient.logEvent(new AuditObject("tcc-status-service", "Traffic Lights Request accepted"));
                LOG.info("Audit Service Response: " + auditResponse.getStatus());
            } catch (Exception e2) {
                LOG.warn("Audit Service call failed: " + e2.getMessage());
        }
        return lights;
    }
}
