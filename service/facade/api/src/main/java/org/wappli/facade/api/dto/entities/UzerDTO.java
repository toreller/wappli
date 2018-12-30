package org.wappli.facade.api.dto.entities;

import lombok.Data;
import org.wappli.facade.api.enums.UzerStatusEnum;
import org.wappli.common.api.rest.dto.entities.EntityDTO;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class UzerDTO implements EntityDTO {
    @NotNull
    private String username;

    private boolean activated;
    private Instant createDate;
    private Instant modifyDate;
    private Instant lastLogin;
    private UzerStatusEnum status;
}
