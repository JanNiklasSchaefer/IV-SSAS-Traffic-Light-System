package de.tub.aot.tcc.priority;

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
import de.tub.aot.locationvalidator.LocationValidationRequest;
import de.tub.aot.locationvalidator.LocationValidationResponse;


@RegisterRestClient(configKey = "location-validator-api")
@OidcClientFilter
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PriorityLocationClient {
    
    @POST
    @Path("/api/location/vehicle")
    public LocationValidationResponse validateVehicleLocation(LocationValidationRequest request);


}

