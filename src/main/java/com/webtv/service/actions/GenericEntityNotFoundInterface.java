package com.webtv.service.actions;

import com.webtv.exception.ParameterizeNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericEntityNotFoundInterface {
    void throwException(String entityName, String id) throws ParameterizeNotFoundException;
    
    <T> T checkId(JpaRepository<T, String> repository, String entityName, String id) throws ParameterizeNotFoundException;
}