package org.wappli.facade.api.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Constraint(validatedBy = {RegistrationEmailValidator.class})
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRegistrationEmail {

    String message() default "invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
