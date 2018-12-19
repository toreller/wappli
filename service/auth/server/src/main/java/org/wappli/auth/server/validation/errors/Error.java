package org.wappli.auth.server.validation.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Error {

    @Getter
    private String attributeName;

    @Setter
    @Getter
    private String error;
}
