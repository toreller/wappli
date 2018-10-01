package org.wappli.common.api.rest.webinterfaces;

import org.springframework.http.ResponseEntity;
import org.wappli.common.api.rest.dto.entities.EntityDTO;
import org.wappli.common.api.rest.dto.input.PageableDTO;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.api.rest.dto.output.IdDTO;

import java.net.URISyntaxException;
import java.util.List;

public interface CrudWebInterface<EDTO extends EntityDTO, CRITERIA> {
    ResponseEntity<IdDTO> create(EDTO dto) throws URISyntaxException;
    ResponseEntity<IdDTO> update(Long id, EDTO dto);
    ResponseEntity<List<EntityWithIdOutputDTO<EDTO>>> getAll(CRITERIA criteria, PageableDTO pageableDTO);
    ResponseEntity<EntityWithIdOutputDTO<EDTO>> get(Long id);
    ResponseEntity<IdDTO> delete(Long id);

}
