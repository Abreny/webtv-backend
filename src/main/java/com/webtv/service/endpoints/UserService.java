package com.webtv.service.endpoints;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.webtv.commons.ResponseModel;
import com.webtv.entity.User;
import com.webtv.entity.UserRole;
import com.webtv.exception.UniqueConstraintException;
import com.webtv.repository.UserRepository;

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
        if(entity.getEmail() == null && entity.getFbId() != null) {
            entity.setEmail(String.format("%s@fb.com", entity.getFbId()));
        }
        if(entity.getEmail() == null) {
            throw new UniqueConstraintException("user", "email");
        }
        this.userRepository.findByEmail(entity.getEmail()).ifPresent((e) -> {
            throw new UniqueConstraintException("user", "email");
        });
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy").withZone(ZoneId.systemDefault());
        if(entity.getPassword() == null) entity.setPassword(String.format("$user%s", formatter.format(Instant.now())));
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
    }
    @Override
    protected void beforeUpdate(User old, User entity) {
        if(entity.getEmail() != null) {
            Optional<User> found= this.userRepository.findByEmail(entity.getEmail());
            if(found.isPresent() && !found.get().getId().equals(old.getId())) {
                throw new UniqueConstraintException("user", "email");
            }
        }
        if(entity.getPassword() != null) old.setPassword(passwordEncoder.encode(entity.getPassword()));
    }

    public ResponseModel<User> changeRole(Long id, int role) {
        User u = findById(id);
        u.setRole(UserRole.of(role));
        return ResponseModel.success(userRepository.save(u));
    }
}