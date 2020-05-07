package com.webtv.commons;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UnauthorizedResponse {

    @JsonProperty("error_code")
    private final String errorCode;

    private final String message;


    public UnauthorizedResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }


    public String getErrorCode() {
        return this.errorCode;
    }


    public String getMessage() {
        return this.message;
    }

    public static UnauthorizedResponse of (String errorCode, String message) {
        return new UnauthorizedResponse(errorCode, message);
    }
}