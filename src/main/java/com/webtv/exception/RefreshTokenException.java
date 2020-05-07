package com.webtv.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Form errors exception
 * 
 * @author abned.fandrena
 *         Apr 27, 2020
 */
public class RefreshTokenException extends AuthenticationException {
    private static final long serialVersionUID = 720178274909869451L;

    private Exception cause;

    public RefreshTokenException(AuthenticationException e) {
        super(e.getMessage());
        this.cause = e;
    }

    public Exception getCause() {
        return cause;
    }
}