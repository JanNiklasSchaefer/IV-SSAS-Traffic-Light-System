package de.tub.aot.client.api;

import io.quarkus.oidc.client.filter.OidcClientFilter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@OidcClientFilter
public interface TccApiClient {

    // Standard APIs (available to all clients)
    @GET
    @Path("/api/status/traffic")
    TrafficStatus getTrafficStatus(@QueryParam("vehicle") Boolean vehicle);

    @POST
    @Path("/api/priority/requests")
    PriorityResponse createPriorityRequest(PriorityRequest request);

    @GET
    @Path("/api/priority/requests/{requestId}")
    RequestStatus getRequestStatus(@PathParam("requestId") String requestId);

    // TCC Operator APIs (Traffic Management Center)
    @PUT
    @Path("/api/state/manual/{intersectionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response setIntersectionState(@PathParam("intersectionId") String intersectionId, IntersectionState state);

    @GET
    @Path("/api/state/manual/{intersectionId}")
    @Produces(MediaType.APPLICATION_JSON)
    IntersectionState getIntersectionState(@PathParam("intersectionId") String intersectionId);

    @POST
    @Path("/api/state/emergency")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response triggerEmergencyMode(EmergencyModeRequest request);
}
