package de.tub.aot.tcc.state;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.quarkus.oidc.client.filter.OidcClientFilter;


import de.tub.aot.common.models.AuditObject;


@RegisterRestClient(configKey = "audit-service-api")
@OidcClientFilter
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PriorityAuditService {
    
    @POST
    @Path("/api/audit/events")
    Response logEvent(AuditObject auditEvent);

    @GET
    @Path("/api/audit/logs")
    public Response getLogs(@QueryParam("from") long from, @QueryParam("to") long to);
}

