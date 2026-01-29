package de.tub.aot.client.api;

import de.tub.aot.common.models.TrafficStatus;
import de.tub.aot.common.models.TrafficLightId;


import io.quarkus.oidc.client.filter.OidcClientFilter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.UUID;
import java.util.ArrayList;

@RegisterRestClient
@OidcClientFilter
public interface TccApiClient {

    @POST
    @Path("/api/priority/requests")
    PriorityResponse createPriorityRequest(PriorityRequest request);

    @GET
    @Path("/api/priority/requests/{requestId}")
    RequestStatus getRequestStatus(@PathParam("requestId") String requestId);

    @GET
    @Path("/api/status/traffic")
    TrafficStatus getTrafficStatus(@QueryParam("traffic-light-id") UUID trafficLightId);

    @GET
    @Path("/api/status/traffic-lights")
    ArrayList<TrafficLightId> getTrafficLights();
}
