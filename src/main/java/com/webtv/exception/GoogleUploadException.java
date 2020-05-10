package com.webtv.exception;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;

@SuppressWarnings("serial")
public class GoogleUploadException extends RuntimeException {
    private final GoogleJsonResponseException cause;

    public GoogleUploadException(GoogleJsonResponseException cause) {
        super(cause);
        this.cause = cause;
    }

    public GoogleJsonResponseException getCause() {
        return cause;
    }
}