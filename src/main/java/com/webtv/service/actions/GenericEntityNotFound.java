package com.webtv.service.actions;

import com.webtv.exception.ParameterizeNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GenericEntityNotFound implements GenericEntityNotFoundInterface {

    @Override
    public void throwException(String entityName, String id) throws ParameterizeNotFoundException {
        throw new ParameterizeNotFoundException(entityName, id);
    }

    @Override
    public <T> T checkId(JpaRepository<T, String> repository, String entityName, String id) throws ParameterizeNotFoundException {
        Optional<T> isExist = id == null ? Optional.empty() : repository.findById(id);
        return isExist.orElseThrow(() -> new ParameterizeNotFoundException(entityName, id));
    }

}