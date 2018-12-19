package org.wappli.admin.api.rest.clients.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.wappli.admin.api.rest.dto.entities.CustomerDTO;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.api.rest.dto.output.IdDTO;
import org.wappli.admin.api.rest.interfaces.CustomerWebInterface;

import java.net.URISyntaxException;
import java.util.List;

@FeignClient(name = "admin", url = "${application.rest.admin}", path = "/backend/api/v1")
public interface CustomerClientFeign {

    String ENTITY_URL = CustomerWebInterface.ENTITY_URL;

    @PostMapping(ENTITY_URL)
    ResponseEntity<IdDTO> create(@RequestBody CustomerDTO customerDTO) throws URISyntaxException;

    @PutMapping(ENTITY_URL + "/{id}")
    ResponseEntity<IdDTO> update(@PathVariable("id") Long id, @RequestBody CustomerDTO customerDTO);

    @GetMapping(ENTITY_URL + "/{id}")
    ResponseEntity<EntityWithIdOutputDTO<CustomerDTO>> get(@PathVariable("id") Long id);

    @DeleteMapping(ENTITY_URL + "/{id}")
    ResponseEntity<IdDTO> delete(@PathVariable("id") Long id);

    @GetMapping(ENTITY_URL)
    ResponseEntity<List<EntityWithIdOutputDTO<CustomerDTO>>> getAll(@RequestParam MultiValueMap<String, String> params);
}
