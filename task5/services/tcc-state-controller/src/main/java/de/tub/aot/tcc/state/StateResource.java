package de.tub.aot.tcc.state;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;


@Path("/api/state")
@ApplicationScoped
@DenyAll
public class StateResource {

    @RestClient 
    TccStateControllerClient tccStateControllerClient;

    @POST
    @Path("/change")
    @RolesAllowed({"tcc-priority-service-controll-service"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeState(StateChangeRequest request) {
        //Response output = tccStateControllerClient.changeState(request.getState());
        return Response.ok().build();
    }

}
