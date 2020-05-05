package com.webtv.service.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * An {@link AuthenticationProvider} implementation that will use provided
 * instance of {@link JwtToken} to perform authentication.
 * 
 * @author abned.fandresena
 *
 * April 26, 2020
 */
@Component
@SuppressWarnings("unchecked")
public class JWTAuthenticationProvider implements AuthenticationProvider {
    private final JWTTokenUtil jwtTokenUtil;
    private final JWTUserDetailsService userDetailsService;

    @Autowired
    public JWTAuthenticationProvider(JWTUserDetailsService userDetailsService, JWTTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String rawAccessToken = (String) authentication.getCredentials();

        Jws<Claims> jwsClaims = jwtTokenUtil.parseClaims(rawAccessToken);
        String subject = jwsClaims.getBody().getSubject();
        List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
        List<GrantedAuthority> authorities = scopes.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        
        UserWrapper context = (UserWrapper) userDetailsService.loadUserByUsername(subject);
        
        return new JWTAuthenticationToken(context.getUser(), authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JWTAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
