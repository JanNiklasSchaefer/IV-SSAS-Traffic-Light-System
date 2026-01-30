package de.tub.aot.client.api;

import de.tub.aot.common.models.TrafficStatus;

import io.quarkus.oidc.client.filter.OidcClientFilter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.ArrayList;
import java.util.UUID;

@RegisterRestClient
@OidcClientFilter
public interface TccApiClient {
    // TCC Operator APIs (Traffic Management Center)
    @PUT
    @Path("/api/state/management/change-state")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response setIntersectionState(@QueryParam("traffic-light-id") UUID trafficLightId, String goalStatus);

    @GET
    @Path("/api/state/management/state")
    @Produces(MediaType.APPLICATION_JSON)
    ArrayList<TrafficStatus> getIntersectionState();
}
