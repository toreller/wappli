package org.wappli.admin.server.web.rest;


import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wappli.admin.api.rest.criteria.CustomerCriteria;
import org.wappli.common.api.rest.dto.input.PageableDTO;
import org.wappli.admin.api.rest.dto.entities.CustomerDTO;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.api.rest.dto.output.IdDTO;
import org.wappli.admin.api.rest.interfaces.CustomerWebInterface;
import org.wappli.admin.server.domain.Customer;
import org.wappli.admin.server.service.CustomerService;
import org.wappli.admin.server.service.mapper.CustomerMapper;
import org.wappli.admin.server.service.query.CustomerQueryService;
import org.wappli.common.server.logging.MessageLogger;
import org.wappli.common.server.web.rest.ResourceWithControllerLogic;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class CustomerResource extends ResourceWithControllerLogic<Customer,
        CustomerDTO,
        CustomerCriteria,
        CustomerService,
        CustomerQueryService,
        CustomerMapper> implements CustomerWebInterface {

    private static final String ENTITY_NAME = "Customer";

    public CustomerResource(CustomerService customerService, CustomerQueryService customerQueryService, CustomerMapper customerMapper, MessageLogger log) {
        super(createControllerLogic(customerService, customerQueryService, customerMapper, log, ENTITY_NAME, ENTITY_URL));
    }

    @Override
    @PostMapping(ENTITY_URL)
    public ResponseEntity<IdDTO> create(@Valid @RequestBody CustomerDTO customerDTO) throws URISyntaxException {
        return controllerLogic.create(customerDTO);
    }

    @Override
    @PutMapping(ENTITY_URL + "/{id}")
    public ResponseEntity<IdDTO> update(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        return controllerLogic.update(id, customerDTO);
    }

    @Override
    @GetMapping(path = ENTITY_URL)
    public ResponseEntity<List<EntityWithIdOutputDTO<CustomerDTO>>> getAll(CustomerCriteria criteria, PageableDTO pageableDTO) {
        return controllerLogic.getAll(criteria, PageRequest.of(pageableDTO.getPage(), pageableDTO.getSize(), getSort(pageableDTO.getSort())));
    }

    @Override
    @GetMapping(path = ENTITY_URL + "/{id}")
    public ResponseEntity<EntityWithIdOutputDTO<CustomerDTO>> get(@PathVariable Long id) {
        return controllerLogic.get(id);
    }

    @Override
    @DeleteMapping(ENTITY_URL + "/{id}")
    public ResponseEntity<IdDTO> delete(@PathVariable Long id) {
        return controllerLogic.delete(id);
    }
}
