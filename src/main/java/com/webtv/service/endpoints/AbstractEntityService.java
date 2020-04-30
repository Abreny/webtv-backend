package com.webtv.service.endpoints;

import java.util.List;

import com.webtv.commons.EntityCopyManager;
import com.webtv.commons.ResponseModel;
import com.webtv.commons.Validator;
import com.webtv.exception.ServerError;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.BindingResult;

public abstract class AbstractEntityService<K,V> {

    private JpaRepository<K,V> repository;

    public AbstractEntityService(JpaRepository<K,V> repository) {
        this.repository = repository;
    }

    public ResponseModel<K> create(K object, BindingResult bindingResult) {
        Validator.checkCreate(bindingResult);
        validateId(object);
        beforeSave(object);
        return ResponseModel.success(repository.save(object));
    }
    
    public ResponseModel<List<K>> list() {
        return ResponseModel.success(repository.findAll());
    }

    public ResponseModel<K> delete(V id) {
        K object = findById(id);
        beforeDelete(object);
        repository.delete(object);
        return ResponseModel.success(object);
    }

    public ResponseModel<K> update(K update, BindingResult bindingResult, V id) {
        Validator.checkUpdate(bindingResult);
        K object = findById(id);
        try {
            new EntityCopyManager().copy(object, update);
        } catch (Exception e) {
            throw new ServerError(e);
        }
        restoreId(object, id);
        beforeUpdate(object, update);
        return ResponseModel.success(repository.save(object));
    }

    protected void validateId(K id) {
        
    }
    protected void restoreId(K entity, V id) {
        
    }

    protected void beforeSave(K entity) {
        
    }

    protected void beforeDelete(K entity) {
        
    }

    protected void beforeUpdate(K old, K entity) {
        
    }

    protected K findById(V object) {
        return null;
    }

    protected final JpaRepository<K,V> getRepository() {
        return repository;
    }
}