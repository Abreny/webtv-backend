package com.webtv.commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.api.client.auth.oauth2.Credential;

public class GoogleCredential {
    private final String token;

    @JsonProperty("refresh_token")
    private final String refreshToken;

    public GoogleCredential(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return this.token;
    }

    public String refreshToken() {
        return this.refreshToken;
    }

    public static GoogleCredential of(Credential credential) {
        return new GoogleCredential(credential.getAccessToken(), credential.getRefreshToken());
    }
}
