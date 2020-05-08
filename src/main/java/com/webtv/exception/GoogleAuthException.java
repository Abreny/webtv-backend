package com.webtv.exception;

@SuppressWarnings("serial")
public class GoogleAuthException extends RuntimeException {
    private final String url;

    public GoogleAuthException(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}