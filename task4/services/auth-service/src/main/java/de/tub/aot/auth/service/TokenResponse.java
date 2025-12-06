package de.tub.aot.auth.service;

import java.math.BigInteger;

public class TokenResponse {

    private String access_token;

    private String refresh_token;

    public long expires_in;
    public long refresh_expires_in;
    public String token_type = "Bearer";

    public TokenResponse() {
    }

    public void setAccessToken(String accessToken) {
        this.access_token = accessToken;
    }

    public String getAccessToken() {
        return this.access_token;
    }

    public void setRefreshToken(String refreshToken) {
        this.refresh_token = refreshToken;
    }

    public String getRefreshToken() {
        return this.refresh_token;
    }
}
