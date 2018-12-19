package org.wappli.auth.server.dto;

import org.wappli.auth.api.dto.input.InputDTO;
import lombok.Data;

@Data
public class UpdateDTO<DTO extends InputDTO> {

    private Long id;

    private DTO inputDTO;
}
