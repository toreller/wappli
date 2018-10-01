package org.wappli.admin.api.rest.clients;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.wappli.admin.api.rest.clients.feign.CustomerClientFeign;
import org.wappli.admin.api.rest.criteria.CustomerCriteria;
import org.wappli.common.api.rest.dto.input.PageableDTO;
import org.wappli.admin.api.rest.dto.entities.CustomerDTO;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.api.rest.dto.output.IdDTO;
import org.wappli.admin.api.rest.interfaces.CustomerWebInterface;
import org.wappli.common.api.rest.util.CriteriaMapBuilder;

import java.net.URISyntaxException;
import java.util.List;

@Component
public class CustomerClient implements CustomerWebInterface {
    private CustomerClientFeign clientFeign;

    public CustomerClient(CustomerClientFeign clientFeign) {
        this.clientFeign = clientFeign;
    }

    @Override
    public ResponseEntity<IdDTO> create(CustomerDTO customerDTO) throws URISyntaxException {
        return clientFeign.create(customerDTO);
    }

    @Override
    public ResponseEntity<IdDTO> update(Long id, CustomerDTO customerDTO) {
        return clientFeign.update(id, customerDTO);
    }

    @Override
    public ResponseEntity<EntityWithIdOutputDTO<CustomerDTO>> get(Long id) {
        return clientFeign.get(id);
    }

    @Override
    public ResponseEntity<IdDTO> delete(Long id) {
        return clientFeign.delete(id);
    }

    @Override
    public ResponseEntity<List<EntityWithIdOutputDTO<CustomerDTO>>> getAll(CustomerCriteria criteria, PageableDTO pageableDTO) {
        return clientFeign.getAll(CriteriaMapBuilder.buildParam(criteria, pageableDTO));
    }
}
