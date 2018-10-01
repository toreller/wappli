package org.wappli.common.api.rest.dto.output;

import org.wappli.common.api.rest.dto.entities.EntityDTO;

import javax.validation.constraints.NotNull;

public class EntityWithIdOutputDTO<EDTO extends EntityDTO> {
    @NotNull
    Long id;

    @NotNull
    EDTO item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EDTO getItem() {
        return item;
    }

    public void setItem(EDTO item) {
        this.item = item;
    }
}
