package com.webtv.service.actions;

import com.webtv.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EntityNotFound implements EntityNotFoundInterface {

    @Override
    public void throwException(String entityName, Long id) throws EntityNotFoundException {
        throw new EntityNotFoundException(entityName, id);
    }

    @Override
    public <T> T checkId(JpaRepository<T, Long> repository, String entityName, Long id) throws EntityNotFoundException {
        Optional<T> isExist = id == null ? Optional.empty() : repository.findById(id);
        if(!isExist.isPresent()) {
            throw new EntityNotFoundException(entityName, id);
        }
        return isExist.get();
    }

}