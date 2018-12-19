package org.wappli.auth.api.dto.input;

import org.wappli.auth.api.validator.ValidRegistrationEmail;
import lombok.Data;
import org.wappli.common.api.rest.dto.entities.EntityDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UzerRegInputDTO implements EntityDTO {

    // todo validator
    @NotNull
    @Size(max = 128)
    private String username;

    // todo validator
    @Size(max = 256)
    private String psw;

    @NotNull
    @Size(max = 128)
    private String email;
}
