package org.wappli.auth.server.web.rest.errors;

import org.wappli.auth.server.validation.errors.Error;
import lombok.Getter;

import java.util.List;

public class InvalidReferenceIdException extends RuntimeException {

    @Getter
    private List<Error> errors;

    public InvalidReferenceIdException(
            List<Error> errors) {
        this.errors = errors;
    }
}
