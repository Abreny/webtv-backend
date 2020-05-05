package com.webtv.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 
 * @author abned.fandresena
 *
 * April 25, 2020
 */
public class JWTExpiredTokenException extends AuthenticationException {
    private static final long serialVersionUID = -5959543783324224864L;

    private String token;

    public JWTExpiredTokenException(String msg) {
        super(msg);
    }

    public JWTExpiredTokenException(String token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token;
    }
}
