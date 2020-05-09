package com.webtv.repository;

import java.util.Optional;

import com.webtv.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByFbId(String fbId);
    Optional<User> findByGoogleId(String googleId);
}