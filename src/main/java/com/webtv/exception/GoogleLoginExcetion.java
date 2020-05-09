package com.webtv.exception;

import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("serial")
public class GoogleLoginExcetion extends AuthenticationException {
    public GoogleLoginExcetion() {
        super("error.authorization.google_id");
    } 
}