package com.webtv.commons;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseModel<T> {
    private int status;
    private String message;
    private boolean error;
    private T data;

    public ResponseModel(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public ResponseModel<T> setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseModel<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isError() {
        return error;
    }

    public ResponseModel<T> setError(boolean hasError) {
        this.error = hasError;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseModel<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static <T> ResponseModel<T> success(T data) {
        return new ResponseModel<T>(data).setStatus(HttpStatus.OK.value()).setMessage("Operation executed successfully")
                .setError(false);
    }
    public static <T> ResponseModel<T> created(T data) {
        return new ResponseModel<T>(data).setStatus(HttpStatus.CREATED.value()).setMessage("Ressource created successfully")
                .setError(false);
    }
    public static <T> ResponseModel<T> unauthorized(T data) {
        return new ResponseModel<T>(data).setStatus(HttpStatus.UNAUTHORIZED.value()).setMessage("Operation unauthorized")
                .setError(true);
    }

	public static <T> ResponseModel<T> badRequest(T data) {
		return new ResponseModel<T>(data).setStatus(HttpStatus.BAD_REQUEST.value()).setMessage("Error caused by client")
                .setError(true);
	}
    public static <T> ResponseModel<T> server_error(T data) {
        return new ResponseModel<T>(data).setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).setMessage("Server error")
                .setError(true);
    }

    public static <T> ResponseEntity<ResponseModel<T>> responseEntity(ResponseModel<T> response) {
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}