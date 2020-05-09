package com.webtv.service.security;

import com.webtv.entity.User;
import com.webtv.exception.GoogleLoginExcetion;
import com.webtv.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * An {@link AuthenticationProvider} implementation that will use provided
 * instance of {@link JwtToken} to perform authentication.
 * 
 * @author abned.fandresena
 *
 *         April 26, 2020
 */
@Component
public class GoogleAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository users;

    @Autowired
    public GoogleAuthenticationProvider(UserRepository users) {
        this.users = users;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String googleId = (String) authentication.getCredentials();
        final User user = this.users.findByGoogleId(googleId).orElseThrow(() -> new GoogleLoginExcetion());
        return new GoogleAuthenticationToken(user,new UserWrapper(user).getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (GoogleAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
