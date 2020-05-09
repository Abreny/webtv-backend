package com.webtv.service.security;

import com.webtv.commons.EntityCopyManager;
import com.webtv.commons.StringUtils;
import com.webtv.entity.User;
import com.webtv.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountUtil {

    @Autowired
    private UserRepository users;

    @Autowired
    private PasswordEncoder encoder;

    public User generateOrUpdate(Authentication auth) {
        User user = new User();
        User found = new User();
        if(auth instanceof FacebookAuthenticationToken) {
            user = (User) auth.getPrincipal();
        }
        if(auth instanceof GoogleAuthenticationToken) {
            user = (User) auth.getPrincipal();
        }
        String email = user.getEmail();
        if(email != null) {
            found = users.findByEmail(email).orElse(user);
        }
        try {
            new EntityCopyManager().copy(found, user);
            if(found.getPassword() == null) {
                found.setPassword(encoder.encode(StringUtils.password()));
            }
        } catch (Exception e) {

        }
        return this.users.save(found);
    }
}