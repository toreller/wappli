package org.wappli.auth.api.dto.input;

import org.wappli.auth.api.validator.ValidRegistrationEmail;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordInputDTO {

    @NotNull
    @Size(max = 256)
    private String newPsw;

    @Size(max = 128)
    @ValidRegistrationEmail
    private String email;
}
