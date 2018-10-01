package org.wappli.common.server.service.mapper;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.wappli.common.api.rest.dto.entities.EntityDTO;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public abstract class MapperUsingRepository<DTO extends EntityDTO, E> implements IOEntityMapper<DTO, E> {

    private Class<E> type;

    public MapperUsingRepository(){
        Type type = getClass().getGenericSuperclass();

        while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != MapperUsingRepository.class) {
            if (type instanceof ParameterizedType) {
                type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
            } else {
                type = ((Class<?>) type).getGenericSuperclass();
            }
        }

        this.type = (Class<E>) ((ParameterizedType) type).getActualTypeArguments()[1];
    }

    public E fromId(Long id) {
        if (id == null) {
            return null;
        }

        Optional<E> entity = getRepository().findById(id);

        if (entity.isEmpty()) {
            throw new EmptyResultDataAccessException(String.format("No %s entity with id %s exists!", getEntitySimpleName(), id), 1);
        }

        return entity.get();
    }

    protected abstract JpaRepository<E, Long> getRepository();

    private String getEntitySimpleName() {
        if(this.type != null){
            return this.type.getSimpleName();
        }
        return "";
    }
}
