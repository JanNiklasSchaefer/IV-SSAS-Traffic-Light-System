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




@RegisterRestClient(configKey = "traffic-light-api")
@OidcClientFilter
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface TccStateControllerClient {
    
    @POST
    @Path("/api/device/change-state")
    Response changeState(@QueryParam("state") String currentState);

    // TODO: Add Other Client Endpoints to reach services internally
}

