package org.wappli.common.server.web.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class CommonResponseEntityExceptionHandler {
    @ExceptionHandler({
            ErrorResponseEntityException.class
    })
    @Nullable
    public final ResponseEntity<Object> handleException(Exception ex, WebRequest request) throws Exception {
        HttpHeaders headers = new HttpHeaders();

		if (ex instanceof ErrorResponseEntityException) {
            HttpStatus status = ((ErrorResponseEntityException) ex).getErrorResponseEntity().getStatusCode();

            return handleErrorResponseEntityException((ErrorResponseEntityException) ex, headers, status, request);
        }
        else {
            throw ex;
        }

    }

    @Nullable
    protected ResponseEntity<Object> handleErrorResponseEntityException(
            ErrorResponseEntityException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {

        return handleExceptionInternal(ex,
                new DescribedResponseEntity(webRequest.getDescription(false), ex.getErrorResponseEntity()),
                headers,
                status,
                webRequest);
    }

    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }
}
