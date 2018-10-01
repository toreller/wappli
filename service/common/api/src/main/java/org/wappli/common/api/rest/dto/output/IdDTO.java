package org.wappli.common.api.rest.dto.output;

import javax.validation.constraints.NotNull;

public class IdDTO {
    @NotNull
    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IdDTO id(long id) {
        this.id = id;

        return this;
    }
}
