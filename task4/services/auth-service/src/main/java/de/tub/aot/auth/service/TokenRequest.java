package de.tub.aot.auth.service;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TokenRequest {
    private String clientId;
    private String clientSecret;

    private String username;
    private String password;

    private String access_token;
    private String refresh_token;

    @Schema(hidden = true)
    private String grant_type;



    public TokenRequest(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}

