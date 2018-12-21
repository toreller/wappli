package org.wappli.auth.server.service.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestMapper {
    String toDto(String entity);
}
