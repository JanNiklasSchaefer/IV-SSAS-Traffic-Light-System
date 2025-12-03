package de.tub.aot.auth.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;


@Path("/api/auth")
@ApplicationScoped
public class AuthenticationService {

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response token(
            @QueryParam("grant_type") String grantType,
            @RequestBody(
                          required = false,
                          description = "Additional parameters depending on the grant type",
                          content = @Content(
                                  schema = @Schema(implementation = TokenRequest.class)
                          )
                  )
                      TokenRequest request) {
        TokenResponse response = new TokenResponse();

        response.setRefreshToken("hardcoded-refresh-token-refresh");
        response.setAccessToken("hardcoded-access-token-refresh");

        return Response.ok(response).build();
    }

    //Endpoint to validate Endpoints. Forward Tokens to Keycloak to validate them and tell microservices if auth request was valid
    @POST
    @Path("/introspect")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response introspect(@QueryParam("access_token") String token) {
        if(introspectToken(token))
            return Response.ok().build();
        else return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path("/userinfo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userinfo(@QueryParam("access_token") String accessToken) {
        UserInfo info = new UserInfo();

        return Response.ok(info).build();
    }

    //Forward token to keycloak to validate via /introspect endpoint

    public Boolean introspectToken(String token) {
        return true;
    }
}
