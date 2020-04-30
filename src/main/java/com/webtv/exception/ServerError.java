package com.webtv.exception;

/**
 * Form errors exception
 * 
 * @author abned.fandrena
 *         Apr 27, 2020
 */
public class ServerError extends RuntimeException {
    private static final long serialVersionUID = 720178274909869451L;

    private Exception cause;

    public ServerError(Exception e) {
        this.cause = e;
    }

    public Exception getCause() {
        return cause;
    }
}