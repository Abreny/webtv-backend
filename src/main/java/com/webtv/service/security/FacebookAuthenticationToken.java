package com.webtv.service.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.webtv.entity.User;

/**
 * An {@link org.springframework.security.core.Authentication} implementation
 * that is designed for simple presentation of JwtToken.
 * 
 * @author abned.fandresena
 *
 *         April 26, 2020
 */
@SuppressWarnings("serial")
public class FacebookAuthenticationToken extends AbstractAuthenticationToken {
    private String fbId;
    private User user;

    public FacebookAuthenticationToken(String fbId) {
        super(null);
        this.fbId = fbId;
        this.setAuthenticated(false);
    }
    public FacebookAuthenticationToken(String fbId, User user) {
        super(null);
        this.fbId = fbId;
        this.user = user;
        this.setAuthenticated(false);
    }
    public FacebookAuthenticationToken(User userContext, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.user = userContext;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return fbId;
    }

    @Override
    public Object getPrincipal() {
        return this.user;
    }

    @Override
    public void eraseCredentials() {        
        super.eraseCredentials();
        this.fbId = null;
    }
}
