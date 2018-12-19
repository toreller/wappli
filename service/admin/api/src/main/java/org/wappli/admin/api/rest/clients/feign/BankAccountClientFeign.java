package org.wappli.admin.api.rest.clients.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.wappli.admin.api.rest.dto.entities.BankAccountDTO;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.api.rest.dto.output.IdDTO;
import org.wappli.admin.api.rest.interfaces.BankAccountWebInterface;

import java.net.URISyntaxException;
import java.util.List;

@FeignClient(name = "admin", url = "${application.rest.admin}", path = "/backend/api/v1")
public interface BankAccountClientFeign {

    String ENTITY_URL = BankAccountWebInterface.ENTITY_URL;

    @PostMapping(ENTITY_URL)
    ResponseEntity<IdDTO> create(@RequestBody BankAccountDTO bankAccountDTO) throws URISyntaxException;

    @PutMapping(ENTITY_URL + "/{id}")
    ResponseEntity<IdDTO> update(@PathVariable("id") Long id, @RequestBody BankAccountDTO bankAccountDTO);

    @GetMapping(ENTITY_URL + "/{id}")
    ResponseEntity<EntityWithIdOutputDTO<BankAccountDTO>> get(@PathVariable("id") Long id);

    @DeleteMapping(ENTITY_URL + "/{id}")
    ResponseEntity<IdDTO> delete(@PathVariable("id") Long id);

    @GetMapping(ENTITY_URL)
    ResponseEntity<List<EntityWithIdOutputDTO<BankAccountDTO>>> getAll(@RequestParam MultiValueMap<String, String> params);
}
