package org.wappli.admin.api.rest.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wappli.admin.api.rest.criteria.BankAccountCriteria;
import org.wappli.admin.api.rest.dto.entities.BankAccountDTO;
import org.wappli.common.api.rest.dto.input.PageableDTO;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.api.rest.dto.output.IdDTO;
import org.wappli.common.api.rest.webinterfaces.CrudWebInterface;

import java.net.URISyntaxException;
import java.util.List;

public interface BankAccountWebInterface extends CrudWebInterface<BankAccountDTO, BankAccountCriteria> {

    String ENTITY_URL = "/bankAccounts";

    @Override
    @PostMapping(ENTITY_URL)
    ResponseEntity<IdDTO> create(@RequestBody BankAccountDTO bankAccountDTO) throws URISyntaxException;

    @Override
    @PutMapping(ENTITY_URL + "/{id}")
    ResponseEntity<IdDTO> update(@PathVariable("id") Long id, @RequestBody BankAccountDTO bankAccountDTO);

    @Override
    @GetMapping(ENTITY_URL)
    ResponseEntity<List<EntityWithIdOutputDTO<BankAccountDTO>>> getAll(BankAccountCriteria criteria, PageableDTO pageableDTO);

    @Override
    @GetMapping(ENTITY_URL + "/{id}")
    ResponseEntity<EntityWithIdOutputDTO<BankAccountDTO>> get(@PathVariable("id") Long id);

    @Override
    @DeleteMapping(ENTITY_URL + "/{id}")
    ResponseEntity<IdDTO> delete(@PathVariable("id") Long id);
}
