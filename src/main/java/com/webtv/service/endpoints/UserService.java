package com.webtv.service.endpoints;

import com.webtv.entity.User;
import com.webtv.exception.UniqueConstraintException;
import com.webtv.retpository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService extends AbstractEntityService<User, Long> {
    public static final String ENTITY_REF = "user";
    
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void beforeSave(User entity) {
        this.userRepository.findByEmail(entity.getEmail()).ifPresent((e) -> {
            throw new UniqueConstraintException("user", "email");
        });
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
    }
}