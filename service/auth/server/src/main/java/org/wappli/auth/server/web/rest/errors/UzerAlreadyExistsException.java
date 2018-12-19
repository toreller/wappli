package org.wappli.auth.server.web.rest.errors;

import lombok.Getter;

public class UzerAlreadyExistsException extends RuntimeException {

    @Getter
    private String email;

    public UzerAlreadyExistsException(String email) {
        super();
        this.email = email;
    }
}
