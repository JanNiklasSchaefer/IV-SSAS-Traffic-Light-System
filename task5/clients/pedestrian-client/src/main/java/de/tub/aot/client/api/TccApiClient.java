package de.tub.aot.client.api;

import io.quarkus.oidc.client.filter.OidcClientFilter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@OidcClientFilter
public interface TccApiClient {

    @GET
    @Path("/api/status/traffic")
    TrafficStatus getTrafficStatus(@QueryParam("vehicle") Boolean vehicle);

    @POST
    @Path("/api/priority/requests")
    PriorityResponse createPriorityRequest(PriorityRequest request);

    @GET
    @Path("/api/priority/requests/{requestId}")
    RequestStatus getRequestStatus(@PathParam("requestId") String requestId);
}
