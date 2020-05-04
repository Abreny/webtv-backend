package com.webtv.exception;

public class UniqueConstraintException extends ParameterizeNotFoundException {
    private static final long serialVersionUID = -382097520568123235L;

    public UniqueConstraintException(String entityName, Object entityId) {
        super(entityName, entityId);
    }
}