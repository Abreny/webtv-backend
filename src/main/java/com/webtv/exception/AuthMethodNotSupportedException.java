package com.webtv.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * 
 * @author abned.fandresena
 *
 * Apr 26, 2020
 */
public class AuthMethodNotSupportedException extends AuthenticationServiceException {
    private static final long serialVersionUID = 3705043083010304496L;

    public AuthMethodNotSupportedException(String msg) {
        super(msg);
    }
}
