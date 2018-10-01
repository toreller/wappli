package org.wappli.common.server.web.rest.controllerlogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wappli.common.api.rest.dto.entities.EntityDTO;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.api.rest.dto.output.IdDTO;
import org.wappli.common.api.rest.util.HasId;
import org.wappli.common.server.domain.AbstractEntity;
import org.wappli.common.server.service.CrudService;
import org.wappli.common.server.service.mapper.IOEntityMapper;
import org.wappli.common.server.service.query.QueryService;
import org.wappli.common.server.web.util.HeaderUtil;
import org.wappli.common.server.web.util.PaginationUtil;
import org.wappli.common.server.web.util.ResponseUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.wappli.common.server.web.util.URIUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.wappli.common.server.config.Constants.BE_API_V;

public class CrudControllerLogic<ENTITY extends AbstractEntity & HasId,
        DTO extends EntityDTO,
        CRITERIA,
        SERVICE extends CrudService<ENTITY>,
        QUERY_SERVICE extends QueryService<ENTITY, CRITERIA>,
        MAPPER extends IOEntityMapper<DTO, ENTITY>> implements ControllerLogic<ENTITY, DTO, CRITERIA, SERVICE, QUERY_SERVICE, MAPPER> {
    private static final Logger log = LoggerFactory.getLogger(CrudControllerLogic.class);

    private final String entityName;
    private final String entityUrl;
    private final SERVICE entityService;
    private final QUERY_SERVICE entityQueryService;
    private final MAPPER entityMapper;

    public CrudControllerLogic(String entityName, String entityUrl, SERVICE entityService, QUERY_SERVICE queryService,
                               MAPPER entityMapper) {
        this.entityName = entityName;
        this.entityUrl = entityUrl;
        this.entityService = entityService;
        this.entityQueryService = queryService;
        this.entityMapper = entityMapper;
    }

    @Override
    public ResponseEntity<IdDTO> create(DTO entityDTO) {
        log.debug("REST request to save {} : {}", entityName, entityDTO);

        ENTITY entity = entityService.create(entityMapper.toEntity(entityDTO));
        long id = entity.getId();

        return ResponseEntity.created(URIUtil.createURI(BE_API_V + entityUrl + "/" + id))
            .body(new IdDTO().id(id));
    }

    @Override
    public ResponseEntity<IdDTO> update(Long id, DTO entityInputDTO) {
        log.debug("REST request to update {} : {} with id {}", entityName, entityInputDTO, id);

        ENTITY entity = entityService.findOne(id);
        if (entity == null) {
            throw createEmptyResultDataAccessException(id);
        }

        entityMapper.merge(entityInputDTO, entity);
        entityService.update(entity);

        return ResponseEntity.ok()
            .body(new IdDTO().id(id));
    }

    @Override
    public ResponseEntity<List<EntityWithIdOutputDTO<DTO>>> getAll(CRITERIA criteria, Pageable pageable) {
        log.debug("REST request to get {}'s by criteria: {}", entityName, criteria);

        if (validatePageable(pageable)) {
            return getEntityPage(criteria, pageable);
        } else {
            return getEntityList(criteria);
        }
    }

    private ResponseEntity<List<EntityWithIdOutputDTO<DTO>>> getEntityList(CRITERIA criteria) {
        List<EntityWithIdOutputDTO<DTO>> entities = entityQueryService.findByCriteria(criteria)
                .stream().map(entityMapper::toDtoWithId).collect(Collectors.toList());

        return ResponseEntity.ok().body(entities);
    }

    private ResponseEntity<List<EntityWithIdOutputDTO<DTO>>> getEntityPage(CRITERIA criteria, Pageable pageable) {
        Page<ENTITY> entities = entityQueryService.findByCriteria(criteria, pageable);
        Page<EntityWithIdOutputDTO<DTO>> page = entities.map(entityMapper::toDtoWithId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, BE_API_V + entityUrl);

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private boolean validatePageable(Pageable pageable) {
        if (pageable.getSort() == null) {
            return false;
        }

        return true;
    }

    @Override
    public ResponseEntity<EntityWithIdOutputDTO<DTO>> get(Long id) {
        log.debug("REST request to get {} : {}", entityName, id);

        ENTITY entity = entityService.findOne(id);

        return ResponseUtil.toResponse(Optional.ofNullable(entityMapper.toDtoWithId(entity)));
    }

    @Override
    public ResponseEntity<IdDTO> delete(Long id) {
        log.debug("REST request to delete {} : {}", entityName, id);

        ENTITY entity = entityService.findOne(id);
        if (entity == null) {
            throw createEmptyResultDataAccessException(id);
        }

        entityService.delete(entity);

        return ResponseEntity.ok().body(new IdDTO().id(id));
    }

    private EmptyResultDataAccessException createEmptyResultDataAccessException(Long id) {
        return new EmptyResultDataAccessException(String.format("%s with ID %s does not exist", entityName, id), 1);
    }
}
