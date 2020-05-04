package com.webtv.service.actions;

import com.webtv.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityNotFoundInterface {
    void throwException(String entityName, Long id) throws EntityNotFoundException;
    
    <T> T checkId(JpaRepository<T, Long> repository, String entityName, Long id) throws EntityNotFoundException;
}