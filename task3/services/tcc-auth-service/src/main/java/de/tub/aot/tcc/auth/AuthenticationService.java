package de.tub.aot.tcc.auth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/api/auth")
@ApplicationScoped
public class AuthenticationService {

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response token(TokenRequest tokenRequest) {
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
    public Response introspect(String token) {
        if(introspectToken(token))
            return Response.ok().build();
        else return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path("/userinfo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userinfo(String accessToken) {
        UserInfo info = new UserInfo();

        return Response.ok(info).build();
    }

    //Forward token to keycloak to validate via /introspect endpoint

    public Boolean introspectToken(String token) {
        return true;
    }
}
