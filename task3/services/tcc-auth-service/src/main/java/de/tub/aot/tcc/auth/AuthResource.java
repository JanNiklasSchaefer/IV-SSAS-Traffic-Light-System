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
public class AuthResource {

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getToken(Object credentials) {
        TokenResponse response = new TokenResponse("hardcoded-token-12345");
        return Response.ok(response).build();
    }

    @POST
    @Path("/refresh")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response refreshToken(Object refreshToken) {
        TokenResponse response = new TokenResponse("hardcoded-refreshed-token-12345");
        return Response.ok(response).build();
    }

    @POST
    @Path("/session")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSessionToken(Object sessionData) {
        TokenResponse response = new TokenResponse("hardcoded-session-token-12345");
        return Response.ok(response).build();
    }

    @POST
    @Path("/authenticate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(Object authData) {
        return Response.ok().build();
    }
}
