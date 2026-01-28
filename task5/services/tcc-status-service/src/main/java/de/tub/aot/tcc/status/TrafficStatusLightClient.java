package de.tub.aot.tcc.status;

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
import de.tub.aot.common.models.TrafficStatus;
import java.util.ArrayList;
import de.tub.aot.common.models.TrafficLightId;

@RegisterRestClient(configKey = "traffic-light-api")
@OidcClientFilter
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface TrafficStatusLightClient {
    // TODO: Add Other Client Endpoints to reach services internally
    @GET
    @Path("/api/device/traffic-state")
    TrafficStatus getState(@QueryParam("vehicle") Boolean vehicle);

    @GET
    @Path("/api/device/traffic-lights")
    ArrayList<TrafficLightId> getLights();
}
