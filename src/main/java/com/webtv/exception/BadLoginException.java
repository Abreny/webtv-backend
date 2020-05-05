package com.webtv.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception for login error
 * @author abned.fandresena
 *         Apr 30, 2020
 */
@SuppressWarnings("serial")
public class BadLoginException extends AuthenticationException {
    
    public BadLoginException(String msg) {
        super(msg);
    }
}