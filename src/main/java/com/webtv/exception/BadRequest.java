package com.webtv.exception;

import java.util.Map;

/**
 * Form errors exception
 * 
 * @author abned.fandrena
 *         Apr 27, 2020
 */
@SuppressWarnings("serial")
public class BadRequest extends RuntimeException {
    private Map<String, String> errors;

    public BadRequest(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> get() {
        return errors;
    }
}