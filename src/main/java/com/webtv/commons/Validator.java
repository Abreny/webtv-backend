package com.webtv.commons;

import java.util.HashMap;
import java.util.Map;

import com.webtv.exception.BadRequest;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Validator utils for a form param.
 * 
 * @author abned.fandresena
 *         Apr 27, 2020
 */
public class Validator {
    public static void checkCreate(BindingResult bindingResult) {
        check(bindingResult, false);
    }

    public static void checkUpdate(BindingResult bindingResult) {
        check(bindingResult, true);
    }

    public static void check(BindingResult validationResult, boolean isUpdate) {
        Map<String, String> errors = getValidationErrors(validationResult, isUpdate);
        if(!errors.isEmpty()) {
            throw new BadRequest(errors);
        }
    }

    public static Map<String, String> getValidationErrors(BindingResult validationResult, Boolean isUpdate) {
        Map<String, String> mapErrors = new HashMap<>();
        if (validationResult.hasErrors()) {
            for (FieldError fieldError : validationResult.getFieldErrors()) {
                if (isUpdate) {
                    if (!"NotNull".equals(fieldError.getCode())) {
                        mapErrors.put(fieldError.getField(),
                                String.format("'%s' %s.", fieldError.getField(), fieldError.getDefaultMessage()));
                    }
                } else {
                    mapErrors.put(fieldError.getField(),
                            String.format("'%s' %s.", fieldError.getField(), fieldError.getDefaultMessage()));
                }
            }
        }
        return mapErrors;
    }
}