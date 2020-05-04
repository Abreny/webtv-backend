package com.webtv.commons;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.webtv.entity.User;

public class LoginResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final User user;

    private final String token;

    @JsonProperty("refresh_token")
    private final String refreshToken;

    public LoginResponse(User user, String token, String refreshToken) {
        this.user = user;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    
}