package org.wappli.auth.server.util;

public class EmailConstants {

    public static final String HTML_TEMPLATE_REGISTRATION_CONFIRM = "REGISTRATION";
    public static final String HTML_TEMPLATE_FORGOTTEN_PASSWORD = "FORGOTTEN_PASSWORD";

    public static final String SUBJECT_REGISTRATION_CONFIRM = "SUBJECT_REGISTRATION";
    public static final String SUBJECT_FORGOTTEN_PASSWORD = "SUBJECT_FORGOTTEN_PASSWORD";

    public static final String VARIABLE_CONFIRM_URL = "confirmUrl";
    public static final String VARIABLE_FORGOTTEN_PASSWORD_URL = "forgottenPasswordUrl";

    private EmailConstants() {
    }
}
