package com.webtv.exception;

public class EntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String entityName;
    private Long entityId;

    public EntityNotFoundException(String entityName, Long entityId) {
        this.entityName = entityName;
        this.entityId = entityId;
    }

    public EntityNotFoundException() {
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    
}