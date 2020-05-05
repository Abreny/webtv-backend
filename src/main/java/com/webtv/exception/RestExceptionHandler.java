package com.webtv.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.SecureRandom;
import java.util.Map;

import com.webtv.commons.ResponseDataBuilder;
import com.webtv.commons.ResponseModel;
import com.webtv.service.Translator;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private Translator translator;

    @ExceptionHandler(value = { InvalidToken.class, InvalidAuthorizationHeader.class,
            AuthMethodNotSupportedException.class, JWTExpiredTokenException.class, BadLoginException.class })
    protected ResponseModel<String> handleDataIntegrityxception(AuthenticationException ex, WebRequest request) {
        return ResponseModel.unauthorized(ex.getMessage());
    }

    @ExceptionHandler(value = { BadRequest.class })
    protected ResponseModel<Map<String, String>> handleValidatorException(BadRequest ex, WebRequest request) {
        return ResponseModel.badRequest(ex.get());
    }

    @ExceptionHandler(value = { ServerError.class })
    protected ResponseModel<Exception> handleServerException(ServerError ex, WebRequest request) {
        return ResponseModel.server_error(ex.getCause());
    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    protected ResponseModel<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex,
            WebRequest request) {
        return ResponseModel
                .badRequest(ResponseDataBuilder
                        .of(ex.getEntityName(),
                                this.translator.get("not.found",
                                        String.format("%s %d", ex.getEntityName().replace('_', ' '), ex.getEntityId())))
                        .get());
    }

    @ExceptionHandler(value = { UniqueConstraintException.class })
    protected ResponseModel<Map<String, String>> handleUniqueConstraintsException(UniqueConstraintException ex,
            WebRequest request) {
        return ResponseModel
                .badRequest(ResponseDataBuilder
                        .of(ex.getEntityName(),
                                this.translator.get("error.unique",
                                        String.format("%s %s", ex.getEntityName().replace('_', ' '), ex.getEntityId())))
                        .get());
    }

    @ExceptionHandler(value = { ParameterizeNotFoundException.class })
    protected ResponseModel<Map<String, String>> handlegenericNotFoundException(ParameterizeNotFoundException ex,
            WebRequest request) {
        return ResponseModel
                .badRequest(ResponseDataBuilder
                        .of(ex.getEntityName(),
                                this.translator.get("not.found",
                                        String.format("%s %s", ex.getEntityName().replace('_', ' '), ex.getEntityId())))
                        .get());
    }
//     @ExceptionHandler(value = { GoogleAuthException.class })
//     protected ResponseModel<Map<String, String>> handleGoogleAuthException(GoogleAuthException ex,
//             WebRequest request) {
//         final String code = String.format("google;%d", new SecureRandom().nextInt());
//         return ResponseModel.success(ex.getUrl().setState(code).build());
//     }
}