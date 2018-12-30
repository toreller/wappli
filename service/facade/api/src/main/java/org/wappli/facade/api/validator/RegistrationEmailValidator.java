package org.wappli.facade.api.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class RegistrationEmailValidator implements ConstraintValidator<ValidRegistrationEmail, String> {

    @Value("${registration.email.pattern}")
    private String pattern;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (!StringUtils.isEmpty(email)) {
            return Pattern.matches(pattern, email);
        }
        return true;
    }
}
