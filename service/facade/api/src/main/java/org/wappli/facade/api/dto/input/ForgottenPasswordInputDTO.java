package org.wappli.facade.api.dto.input;

import lombok.Data;
import org.wappli.facade.api.validator.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ForgottenPasswordInputDTO {

    @NotNull
    @Size(max = 128)
    @ValidRegistrationEmail
    private String email;
}
