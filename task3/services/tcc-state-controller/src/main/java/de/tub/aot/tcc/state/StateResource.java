package de.tub.aot.tcc.state;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/state")
@ApplicationScoped
public class StateResource {

    @POST
    @Path("/change")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeState(Object stateCommand) {
        return Response.ok().build();
    }

    @POST
    @Path("/device/change-state")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeDeviceState(StateChangeRequest request) {
        return Response.ok().build();
    }

    @POST
    @Path("/audit/events")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logAuditEvent(Object auditEvent) {
        return Response.ok().build();
    }
}
