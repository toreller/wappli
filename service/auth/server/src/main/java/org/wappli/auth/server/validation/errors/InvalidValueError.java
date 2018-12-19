package org.wappli.auth.server.validation.errors;

import lombok.Getter;

public class InvalidValueError extends Error {

    @Getter
    private Object value;

    public InvalidValueError(String name, Object value) {
        super(name, "invalid");
        this.value = value;
    }

    public InvalidValueError(String name, String error, Object value) {
        super(name, error);
        this.value = value;
    }

}
