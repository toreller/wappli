package org.wappli.auth.server.web.rest.errors.handler;

import org.wappli.auth.server.web.rest.errors.ErrorConstants;
import org.wappli.auth.server.web.rest.errors.InvalidReferenceIdException;
import org.wappli.auth.server.web.rest.errors.UzerAlreadyExistsException;
import org.wappli.auth.server.web.rest.errors.UzerNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@ControllerAdvice
public class BusinessExceptionHandler extends AbstractExceptionHandler {

    @ExceptionHandler(UzerAlreadyExistsException.class)
    public ResponseEntity<Problem> handleUzerAlreadyExistsException(
            UzerAlreadyExistsException ex, NativeWebRequest request) {

        final Problem problem = build(ErrorConstants.DEFAULT_TYPE, Status.BAD_REQUEST,
                ErrorConstants.ERR_USER_ALREADY_EXISTS);

        return create(ex, problem, request);
    }

    @ExceptionHandler(InvalidReferenceIdException.class)
    public ResponseEntity<Problem> handleInvalidReferenceIdException(InvalidReferenceIdException ex,
                                                                     NativeWebRequest request) {
        final Problem problem = build(ErrorConstants.CONSTRAINT_VIOLATION_TYPE, Status.BAD_REQUEST,
                ErrorConstants.ERR_VALIDATION, ex.getErrors());

        return create(ex, problem, request);
    }

    @ExceptionHandler(UzerNotFoundException.class)
    public ResponseEntity<Problem> handleUzerNotFoundException(UzerNotFoundException ex, NativeWebRequest request) {
        final Problem problem = build(ErrorConstants.DEFAULT_TYPE, Status.NOT_FOUND,
                ErrorConstants.ERR_USER_NOT_FOUND);

        return create(ex, problem, request);
    }
}
