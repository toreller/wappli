package org.wappli.common.server.service.mapper;

import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.wappli.common.api.rest.dto.entities.EntityDTO;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;

public interface IOEntityMapper<EDTO extends EntityDTO, E> {

    @Mappings({
            @Mapping(target = "id", ignore = true),
    })

    E toEntity(EDTO eDTO);

    void merge(EDTO eDTO, @MappingTarget E entity);

    @Mapping(source = "id", target = "id")
    EntityWithIdOutputDTO<EDTO> toDtoWithId(E entity);

    EDTO toDto(E entity);
}
