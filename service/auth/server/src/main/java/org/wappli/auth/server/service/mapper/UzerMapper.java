package org.wappli.auth.server.service.mapper;

import org.wappli.auth.api.dto.entities.UzerDTO;
import org.wappli.auth.api.dto.input.UzerRegInputDTO;
import org.wappli.auth.api.dto.output.UzerAuth;
import org.wappli.auth.api.enums.UzerStatusEnum;
import org.wappli.auth.server.domain.Uzer;
import org.wappli.auth.server.repository.UzerRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.server.service.mapper.MapperUsingRepository;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring", imports = {UzerStatusEnum.class})
public abstract class UzerMapper extends MapperUsingRepository<UzerDTO, Uzer> {

    @Autowired
    private UzerRepository uzerRepository;

    @Override
    public abstract Uzer toEntity(UzerDTO eDTO);

    @Override
    public abstract void merge(UzerDTO eDTO, Uzer entity);

    @Override
    public abstract EntityWithIdOutputDTO<UzerDTO> toDtoWithId(Uzer entity);

    @Override
    public abstract UzerDTO toDto(Uzer entity);

    @Mapping(expression = "java(false)", target = "activated")
    @Mapping(expression = "java(UzerStatusEnum.ACTIVE)", target = "userStatus")
    public abstract Uzer toEntity(UzerRegInputDTO iDTO, String activationHash, Instant activateDeadline);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "username")
    @Mapping(source = "psw", target = "password")
    @Mapping(expression = "java(true)", target = "accountNonExpired")
    @Mapping(expression = "java(true)", target = "accountNonLocked")
    @Mapping(expression = "java(true)", target = "credentialsNonExpired")
    @Mapping(expression = "java(isUserEnabled(entity))", target = "enabled")
    @Mapping(expression = "java(getRoles(entity))", target = "authorities")
    public abstract UzerAuth toAuthDTO(Uzer entity);


    protected boolean isUserEnabled(Uzer uzer) {
        return uzer.isActivated() && UzerStatusEnum.ACTIVE.equals(uzer.getUserStatus());
    }

    protected Set<String> getRoles(Uzer uzer) {
        if (uzer.getEmail().contains("admin")) {
            return new HashSet<>(Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
        }
        return new HashSet<>(Arrays.asList("ROLE_USER"));
    }

    @ObjectFactory
    Uzer getExistingInstance(Long id) {
        if (id == null) {
            return new Uzer();
        }
        final Uzer uzer = fromId(id);

        return toNewInstance(uzer);
    }

    protected abstract Uzer toNewInstance(Uzer uzer);

    @Override
    protected JpaRepository<Uzer, Long> getRepository() {
        return uzerRepository;
    }
}
