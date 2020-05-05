package com.webtv.service.security.extractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.webtv.commons.StringUtils;
import com.webtv.exception.InvalidAuthorizationHeader;
import com.webtv.service.Translator;

/**
 * An implementation of {@link TokenExtractor} extracts token from
 * Authorization: Bearer scheme.
 * 
 * @author abned.fandresena
 *
 * April 25, 2020
 */
@Component
public class JWTHeaderTokenExtractor implements TokenExtractor {
    public static String HEADER_PREFIX = "Bearer ";

    @Autowired
    private Translator translator;

    @Override
    public String extract(String header) {
        if (StringUtils.isBlank(header)) {
            throw new InvalidAuthorizationHeader(translator.get("error.authorization.header.blank"));
        }

        if (header.length() < HEADER_PREFIX.length()) {
            throw new InvalidAuthorizationHeader(translator.get("error.authorization.header.size"));
        }

        if (!HEADER_PREFIX.equals(header.substring(0, HEADER_PREFIX.length()))) {
            throw new InvalidAuthorizationHeader(translator.get("error.authorization.header.invalid"));
        }

        return header.substring(HEADER_PREFIX.length(), header.length());
    }
}
