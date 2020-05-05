package com.webtv.exception;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;

@SuppressWarnings("serial")
public class GoogleAuthException extends RuntimeException {
    private final GoogleAuthorizationCodeRequestUrl url;

    public GoogleAuthException(GoogleAuthorizationCodeRequestUrl url) {
        this.url = url;
    }

    public GoogleAuthorizationCodeRequestUrl getUrl() {
        return url;
    }
}