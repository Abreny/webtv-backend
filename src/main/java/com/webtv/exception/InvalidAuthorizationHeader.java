package com.webtv.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * JwtTokenNotValid
 * 
 * @author abned.fandresena
 *
 * Apr 26, 2020
 */
public class InvalidAuthorizationHeader extends AuthenticationException {
    private static final long serialVersionUID = -294671188037098603L;
    
    public InvalidAuthorizationHeader(String msg) {
        super(msg);
    }
}
