package com.webtv.service.security;

import com.webtv.entity.User;

import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityHelper {
    public static User user() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}