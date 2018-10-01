package org.wappli.admin.server.web.rest;


import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wappli.admin.api.rest.criteria.BankAccountCriteria;
import org.wappli.common.api.rest.dto.input.PageableDTO;
import org.wappli.admin.api.rest.dto.entities.BankAccountDTO;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.api.rest.dto.output.IdDTO;
import org.wappli.admin.api.rest.interfaces.BankAccountWebInterface;
import org.wappli.admin.server.domain.BankAccount;
import org.wappli.admin.server.service.BankAccountService;
import org.wappli.admin.server.service.mapper.BankAccountMapper;
import org.wappli.admin.server.service.query.BankAccountQueryService;
import org.wappli.common.server.logging.MessageLogger;
import org.wappli.common.server.web.rest.ResourceWithControllerLogic;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class BankAccountResource extends ResourceWithControllerLogic<BankAccount,
        BankAccountDTO,
        BankAccountCriteria,
        BankAccountService,
        BankAccountQueryService,
        BankAccountMapper> implements BankAccountWebInterface {

    private static final String ENTITY_NAME = "BankAccount";

    public BankAccountResource(BankAccountService bankAccountService, BankAccountQueryService bankAccountQueryService, BankAccountMapper bankAccountMapper, MessageLogger log) {
        super(createControllerLogic(bankAccountService, bankAccountQueryService, bankAccountMapper, log, ENTITY_NAME, ENTITY_URL));
    }

    @Override
    @PostMapping(ENTITY_URL)
    public ResponseEntity<IdDTO> create(@Valid @RequestBody BankAccountDTO bankAccountDTO) throws URISyntaxException {
        return controllerLogic.create(bankAccountDTO);
    }

    @Override
    @PutMapping(ENTITY_URL + "/{id}")
    public ResponseEntity<IdDTO> update(@PathVariable Long id, @Valid @RequestBody BankAccountDTO bankAccountDTO) {
        return controllerLogic.update(id, bankAccountDTO);
    }

    @Override
    @GetMapping(ENTITY_URL)
    public ResponseEntity<List<EntityWithIdOutputDTO<BankAccountDTO>>> getAll(BankAccountCriteria criteria, PageableDTO pageableDTO) {
        return controllerLogic.getAll(criteria, PageRequest.of(pageableDTO.getPage(), pageableDTO.getSize(), getSort(pageableDTO.getSort())));
    }

    @Override
    @GetMapping(ENTITY_URL + "/{id}")
    public ResponseEntity<EntityWithIdOutputDTO<BankAccountDTO>> get(@PathVariable Long id) {
        return controllerLogic.get(id);
    }

    @Override
    @DeleteMapping(ENTITY_URL + "/{id}")
    public ResponseEntity<IdDTO> delete(@PathVariable Long id) {
        return controllerLogic.delete(id);
    }
}
