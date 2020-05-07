package com.webtv.entity;

import java.util.stream.Stream;

public enum UserRole {
    ADMIN(2), USER(1);

    private int value;

    UserRole(int val) {
        value = val;
    }

    public int value() {
        return value;
    }

    public static UserRole of(int val) {
        return Stream.of(UserRole.values()).filter(p -> p.value() == val).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}