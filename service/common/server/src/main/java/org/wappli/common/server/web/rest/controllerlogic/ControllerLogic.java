package org.wappli.common.server.web.rest.controllerlogic;

import org.wappli.common.api.rest.dto.entities.EntityDTO;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.api.rest.dto.output.IdDTO;
import org.wappli.common.api.rest.util.HasId;
import org.wappli.common.server.domain.AbstractEntity;
import org.wappli.common.server.service.CrudService;
import org.wappli.common.server.service.mapper.IOEntityMapper;
import org.wappli.common.server.service.query.QueryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.List;

public interface ControllerLogic<ENTITY extends AbstractEntity & HasId, EDTO extends EntityDTO,
        CRITERIA, SERVICE extends CrudService<ENTITY>, QUERY_SERVICE extends QueryService<ENTITY, CRITERIA>,
        MAPPER extends IOEntityMapper<EDTO, ENTITY>> {
    ResponseEntity<IdDTO> create(EDTO inputDTO) throws URISyntaxException;
    ResponseEntity<IdDTO> update(Long id, EDTO entityInputDTO);
    ResponseEntity<List<EntityWithIdOutputDTO<EDTO>>> getAll(CRITERIA criteria, Pageable pageable);
    ResponseEntity<EntityWithIdOutputDTO<EDTO>> get(Long id);
    ResponseEntity<IdDTO> delete(Long id);
}
