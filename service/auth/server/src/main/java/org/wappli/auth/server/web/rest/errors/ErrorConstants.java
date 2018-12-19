package org.wappli.auth.server.web.rest.errors;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String ERR_CONSTRAIN_VIOLATION = "error.constraint.violation";
    public static final String ERR_USER_ALREADY_EXISTS = "error.user.exists";
    public static final String ERR_USER_NOT_FOUND = "error.user.notFound";
    public static final String PROBLEM_BASE_URL = "";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI
            .create(PROBLEM_BASE_URL + "");

    public static final String ERR_INTERNAL_SERVER_ERROR = "internal.server.error";

    private ErrorConstants() {
    }
}
