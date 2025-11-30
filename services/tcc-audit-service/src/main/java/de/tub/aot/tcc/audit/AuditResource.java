package de.tub.aot.tcc.audit;

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
import java.util.List;

@Path("/api/audit")
@ApplicationScoped
public class AuditResource {

    private List<Object> auditLogs = new ArrayList<>();

    @POST
    @Path("/events")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logEvent(Object auditEvent) {
        auditLogs.add(auditEvent);
        return Response.ok().build();
    }

    @GET
    @Path("/logs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogs(@QueryParam("from") String from, @QueryParam("to") String to) {
        return Response.ok(auditLogs).build();
    }
}
