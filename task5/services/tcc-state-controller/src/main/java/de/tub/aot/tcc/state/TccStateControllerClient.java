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
import java.util.UUID;
import java.util.ArrayList;

import de.tub.aot.common.models.TrafficStatus;
import de.tub.aot.common.models.TrafficLightId;


@RegisterRestClient(configKey = "traffic-light-api")
@OidcClientFilter
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface TccStateControllerClient {

    @POST
    @Path("/api/device/change-state")
    @Produces(MediaType.APPLICATION_JSON)
    Response changeState();

    @POST
    @Path("/api/device/management/change-state")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response managementChangeState(TrafficStatus goalStatus);

    @GET
    @Path("/api/device/traffic-state")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    TrafficStatus getTrafficState(@QueryParam("traffic-light-id") UUID trafficLightId);

    @GET
    @Path("/api/device/traffic-lights")
    @Produces(MediaType.APPLICATION_JSON)
    ArrayList<TrafficLightId> getTrafficLights();

    // TODO: Add Other Client Endpoints to reach services internally
}
