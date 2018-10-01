package org.wappli.admin.server.web.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.wappli.admin.api.rest.clients.BankAccountClient;
import org.wappli.admin.api.rest.criteria.BankAccountCriteria;
import org.wappli.admin.api.rest.dto.TestDTOFactory;
import org.wappli.admin.api.rest.dto.entities.BankAccountDTO;
import org.wappli.common.server.web.integration.AbstractClientIntTest;

public class BankAccountClientIntTest extends AbstractClientIntTest<BankAccountDTO, BankAccountCriteria, BankAccountClient> {
    @Autowired
    private BankAccountClient bankAccountClient;

    @Override
    public void setClient() {
        super.client = this.bankAccountClient;
    }

    @Override
    protected BankAccountDTO createEntityDTO() {
        return TestDTOFactory.createBankAccountDTO();
    }

    @Override
    protected BankAccountCriteria createCriteria() {
        return new BankAccountCriteria();
    }
}
