package org.wappli.auth.api.dto.input;

import org.wappli.auth.api.validator.ValidRegistrationEmail;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ForgottenPasswordInputDTO {

    @NotNull
    @Size(max = 128)
    @ValidRegistrationEmail
    private String email;
}
