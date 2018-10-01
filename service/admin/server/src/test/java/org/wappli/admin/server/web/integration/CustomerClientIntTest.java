package org.wappli.admin.server.web.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.wappli.admin.api.rest.clients.CustomerClient;
import org.wappli.admin.api.rest.criteria.CustomerCriteria;
import org.wappli.admin.api.rest.dto.TestDTOFactory;
import org.wappli.admin.api.rest.dto.entities.CustomerDTO;
import org.wappli.common.server.web.integration.AbstractClientIntTest;

public class CustomerClientIntTest extends AbstractClientIntTest<CustomerDTO, CustomerCriteria, CustomerClient> {
    @Autowired
    private CustomerClient customerClient;

    @Override
    public void setClient() {
        super.client = this.customerClient;
    }

    @Override
    protected CustomerDTO createEntityDTO() {
        return TestDTOFactory.createCustomerDTO();
    }

    @Override
    protected CustomerCriteria createCriteria() {
        return new CustomerCriteria();
    }
}
