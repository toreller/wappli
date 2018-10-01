package org.wappli.admin.api.rest.dto.entities;

import org.wappli.common.api.rest.dto.entities.EntityDTO;

import javax.validation.constraints.NotNull;

public class CustomerDTO implements EntityDTO {
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
