package org.wappli.admin.api.rest.clients;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.wappli.admin.api.rest.clients.feign.BankAccountClientFeign;
import org.wappli.admin.api.rest.criteria.BankAccountCriteria;
import org.wappli.admin.api.rest.dto.entities.BankAccountDTO;
import org.wappli.common.api.rest.dto.input.PageableDTO;
import org.wappli.admin.api.rest.interfaces.BankAccountWebInterface;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.api.rest.dto.output.IdDTO;
import org.wappli.common.api.rest.util.CriteriaMapBuilder;

import java.net.URISyntaxException;
import java.util.List;

@Component
public class BankAccountClient implements BankAccountWebInterface {
    private BankAccountClientFeign clientFeign;

    public BankAccountClient(BankAccountClientFeign clientFeign) {
        this.clientFeign = clientFeign;
    }

    @Override
    public ResponseEntity<IdDTO> create(BankAccountDTO bankAccountDTO) throws URISyntaxException {
        return clientFeign.create(bankAccountDTO);
    }

    @Override
    public ResponseEntity<IdDTO> update(Long id, BankAccountDTO bankAccountDTO) {
        return clientFeign.update(id, bankAccountDTO);
    }

    @Override
    public ResponseEntity<EntityWithIdOutputDTO<BankAccountDTO>> get(Long id) {
        return clientFeign.get(id);
    }

    @Override
    public ResponseEntity<IdDTO> delete(Long id) {
        return clientFeign.delete(id);
    }

    @Override
    public ResponseEntity<List<EntityWithIdOutputDTO<BankAccountDTO>>> getAll(BankAccountCriteria criteria, PageableDTO pageableDTO) {
        return clientFeign.getAll(CriteriaMapBuilder.buildParam(criteria, pageableDTO));
    }
}
