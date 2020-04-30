package com.webtv.exception;

public class ParameterizeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String entityName;
    private Object entityId;

    public ParameterizeNotFoundException(String entityName, Object entityId) {
        this.entityName = entityName;
        this.entityId = entityId;
    }

    public ParameterizeNotFoundException() {
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Object getEntityId() {
        return entityId;
    }

    public void setEntityId(Object entityId) {
        this.entityId = entityId;
    }

    
}