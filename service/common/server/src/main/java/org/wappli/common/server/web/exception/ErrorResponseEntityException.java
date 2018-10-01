package org.wappli.common.server.web.exception;

import org.springframework.http.ResponseEntity;

public class ErrorResponseEntityException extends RuntimeException {
    private ResponseEntity<?> errorResponseEntity;

    public ErrorResponseEntityException(ResponseEntity<?> errorResponseEntity) {
        this.errorResponseEntity = errorResponseEntity;
    }

    public ResponseEntity<?> getErrorResponseEntity() {
        return errorResponseEntity;
    }
}
