package de.tub.aot.tcc.audit;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import de.tub.aot.common.models.AuditObject;


@Path("/api/audit")
@ApplicationScoped
@DenyAll
public class AuditResource {

    private ArrayList<AuditObject> auditLogs = new ArrayList<>();

    @POST
    @Path("/events")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"tcc-priority-service-audit-role","tcc-state-controller-audit-role"})
    public Response logEvent(AuditObject auditEvent) {
        auditLogs.add(auditEvent);
        return Response.ok().build();
    }

    @GET
    @Path("/logs")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"tcc-priority-service-audit-role","tcc-state-controller-audit-role"})
    public Response getLogs(@QueryParam("from") long from, @QueryParam("to") long to) {
        // basic validation
        if (from < 0 || to < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("from/to must be epoch milliseconds (>= 0)")
                    .build();
        }
        if (to < from) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("'to' must be >= 'from'")
                    .build();
        }

        ArrayList<AuditObject> filtered = new ArrayList<>();
        for (AuditObject log : auditLogs) {
            long ts = log.getTimestamp();
            if (ts >= from && ts <= to) {
                filtered.add(log);
            }
        }

        return Response.ok(filtered).build();
    }
}
