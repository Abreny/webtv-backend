package com.webtv.service.security.extractor;

/**
 * Implementations of this interface should always return raw base-64 encoded
 * representation of JWT Token.
 * 
 * @author abned.fandresena
 *
 * April 25, 2020
 */
public interface TokenExtractor {
    public String extract(String payload);
}
