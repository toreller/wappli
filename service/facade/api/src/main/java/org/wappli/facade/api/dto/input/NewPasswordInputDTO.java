package org.wappli.facade.api.dto.input;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewPasswordInputDTO {

    @NotNull
    @Size(max = 256)
    private String newPsw;

    @NotNull
    @Size(max = 256)
    private String activationHash;
}
