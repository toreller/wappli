package org.wappli.common.server.service.mapper;

import org.wappli.common.api.rest.dto.entities.EntityDTO;

public abstract class MapperNotUsingRepository<DTO extends EntityDTO, E> implements IOEntityMapper<DTO, E> {

    public MapperNotUsingRepository(){
    }

    public E fromId(Long id) {
        if (id == null) {
            return null;
        }

        E entity = createEntity();

        setId(id, entity);

        return entity;
    }

    protected abstract E createEntity();

    protected abstract void setId(Long id, E entity);
}
