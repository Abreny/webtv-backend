package com.webtv.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

import com.webtv.commons.ResponseDataBuilder;
import com.webtv.commons.ResponseModel;
import com.webtv.commons.UnauthorizedResponse;
import com.webtv.service.Translator;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private Translator translator;
    
    @ExceptionHandler(value = { InvalidToken.class, InvalidAuthorizationHeader.class,
            AuthMethodNotSupportedException.class, JWTExpiredTokenException.class })
    protected ResponseEntity<ResponseModel<UnauthorizedResponse>> handleInvalidTokeException(AuthenticationException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseModel.unauthorized(UnauthorizedResponse.of("BAD_TOKEN", ex.getMessage())));
    }

    @ExceptionHandler(value = { BadLoginException.class })
    protected ResponseEntity<ResponseModel<UnauthorizedResponse>> handleBadLoginException(AuthenticationException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseModel.unauthorized(UnauthorizedResponse.of("BAD_LOGIN", ex.getMessage())));
    }

    @ExceptionHandler(value = { RefreshTokenException.class })
    protected ResponseEntity<ResponseModel<UnauthorizedResponse>> handleRefreshTokenException(AuthenticationException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseModel.unauthorized(UnauthorizedResponse.of("BAD_REFRESH_TOKEN", ex.getMessage())));
    }
    
    @ExceptionHandler(value = { BadRequest.class })
    protected ResponseEntity<ResponseModel<Map<String, String>>> handleValidatorException(BadRequest ex, WebRequest request) {
        return ResponseModel.responseEntity(ResponseModel.badRequest(ex.get()));
    }
    
    @ExceptionHandler(value = { ServerError.class })
    protected ResponseEntity<ResponseModel<Exception>> handleServerException(ServerError ex, WebRequest request) {
        return ResponseModel.responseEntity(ResponseModel.server_error(ex.getCause()));
    }
    
    @ExceptionHandler(value = { EntityNotFoundException.class })
    protected ResponseEntity<ResponseModel<Map<String, String>>> handleEntityNotFoundException(EntityNotFoundException ex,
    WebRequest request) {
        return ResponseModel.responseEntity(ResponseModel
        .badRequest(ResponseDataBuilder
        .of(ex.getEntityName(),
        this.translator.get("not.found",
        String.format("%s %d", ex.getEntityName().replace('_', ' '), ex.getEntityId())))
        .get()));
    }
    
    @ExceptionHandler(value = { UniqueConstraintException.class })
    protected ResponseEntity<ResponseModel<Map<String, String>>> handleUniqueConstraintsException(UniqueConstraintException ex,
    WebRequest request) {
        return ResponseModel.responseEntity(
            ResponseModel
            .badRequest(ResponseDataBuilder
            .of(ex.getEntityName(),
            this.translator.get("error.unique",
            String.format("%s %s", ex.getEntityName().replace('_', ' '), ex.getEntityId())))
            .get())
            );
        }
        
        @ExceptionHandler(value = { ParameterizeNotFoundException.class })
        protected ResponseEntity<ResponseModel<Map<String, String>>> handlegenericNotFoundException(ParameterizeNotFoundException ex,
        WebRequest request) {
            return ResponseModel.responseEntity(ResponseModel
            .badRequest(ResponseDataBuilder
            .of(ex.getEntityName(),
            this.translator.get("not.found",
            String.format("%s %s", ex.getEntityName().replace('_', ' '), ex.getEntityId())))
            .get()));
        }
        @ExceptionHandler(value = { GoogleAuthException.class })
        protected ResponseEntity<ResponseModel<UnauthorizedResponse>> handleGoogleAuthException(GoogleAuthException ex,
        WebRequest request) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseModel.unauthorized(UnauthorizedResponse.of("GOOGLE_CREDENTIAL", ex.getUrl())));
        }
    }