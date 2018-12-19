package org.wappli.auth.server.web.rest.errors.handler;

import org.wappli.auth.server.validation.errors.Error;
import org.wappli.auth.server.validation.errors.InvalidValueError;
import org.wappli.auth.server.web.rest.errors.ErrorConstants;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.annotation.Nonnull;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Controller advice to translate the server side exceptions to client-friendly json structures. The
 * error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807)
 */
@ControllerAdvice
public class DefaultExceptionHandler extends AbstractExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Problem> handleMethodArgumentNotValid(RuntimeException ex,
                                                                @Nonnull NativeWebRequest request) {
        final Problem problem = build(ErrorConstants.DEFAULT_TYPE, Status.INTERNAL_SERVER_ERROR,
                ErrorConstants.ERR_INTERNAL_SERVER_ERROR);

        return create(ex, problem, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Problem> handleMethodArgumentNotValid(ConstraintViolationException ex,
                                                                @Nonnull NativeWebRequest request) {
        final Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        final List<Error> errors = violations.stream()
                .map(violation -> new Error(violation.getPropertyPath().toString(),
                        violation.getMessage())).collect(Collectors.toList());

        final Problem problem = build(ErrorConstants.CONSTRAINT_VIOLATION_TYPE, Status.BAD_REQUEST,
                ErrorConstants.ERR_CONSTRAIN_VIOLATION, errors);

        return create(ex, problem, request);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                @Nonnull NativeWebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<Error> fieldErrors = result.getFieldErrors().stream()
                .map(f -> {

                    if (f.getDefaultMessage() != null) {
                        return new InvalidValueError(f.getField(),
                                f.getDefaultMessage(), f.getRejectedValue());
                    } else {
                        return new InvalidValueError(f.getField(), f.getRejectedValue());
                    }
                })
                .collect(Collectors.toList());

        final Problem problem = build(ErrorConstants.CONSTRAINT_VIOLATION_TYPE, Status.BAD_REQUEST,
                ErrorConstants.ERR_VALIDATION, fieldErrors);

        return create(ex, problem, request);
    }

    @ExceptionHandler(ConcurrencyFailureException.class)
    public ResponseEntity<Problem> handleConcurrencyFailure(ConcurrencyFailureException ex,
                                                            NativeWebRequest request) {

        final Problem problem = build(ErrorConstants.DEFAULT_TYPE, Status.CONFLICT,
                ErrorConstants.ERR_CONCURRENCY_FAILURE);

        return create(ex, problem, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Problem> handleAccessDeniedException(AccessDeniedException ex,
                                                               NativeWebRequest request) {

        final Problem problem = build(ErrorConstants.DEFAULT_TYPE, Status.FORBIDDEN, ex.getMessage());
        return create(ex, problem, request);
    }
}
