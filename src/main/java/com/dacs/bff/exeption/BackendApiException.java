package com.dacs.bff.exeption;

import org.springframework.http.HttpStatus;

public class BackendApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final HttpStatus status;
    private final String code;
    private final String description;

    public BackendApiException(HttpStatus status, String code, String description) {
        super(description);
        this.status = status;
        this.code = code;
        this.description = description;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}