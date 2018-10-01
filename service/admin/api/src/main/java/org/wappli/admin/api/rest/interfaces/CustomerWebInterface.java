package org.wappli.admin.api.rest.interfaces;

import org.springframework.http.ResponseEntity;
import org.wappli.admin.api.rest.criteria.CustomerCriteria;
import org.wappli.common.api.rest.dto.input.PageableDTO;
import org.wappli.admin.api.rest.dto.entities.CustomerDTO;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.api.rest.dto.output.IdDTO;
import org.wappli.common.api.rest.webinterfaces.CrudWebInterface;

import java.net.URISyntaxException;
import java.util.List;

public interface CustomerWebInterface extends CrudWebInterface<CustomerDTO, CustomerCriteria> {

    String ENTITY_URL = "/customers";

    @Override
    ResponseEntity<IdDTO> create(CustomerDTO customerDTO) throws URISyntaxException;

    @Override
    ResponseEntity<IdDTO> update(Long id, CustomerDTO customerDTO);

    @Override
    ResponseEntity<List<EntityWithIdOutputDTO<CustomerDTO>>> getAll(CustomerCriteria criteria, PageableDTO pageableDTO);

    @Override
    ResponseEntity<EntityWithIdOutputDTO<CustomerDTO>> get(Long id);

    @Override
    ResponseEntity<IdDTO> delete(Long id);
}
