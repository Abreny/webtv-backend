package com.webtv.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.webtv.repository.UserRepository;

@Component
public class JWTUserDetailsService implements UserDetailsService {

    private UserRepository users;

    public JWTUserDetailsService(UserRepository users) {
        this.users = users;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserWrapper(this.users.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found")));
    }
}