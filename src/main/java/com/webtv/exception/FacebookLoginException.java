package com.webtv.exception;

import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("serial")
public class FacebookLoginException extends AuthenticationException {
    public FacebookLoginException() {
        super("error.authorization.fb_id");
    }   
}