package org.wappli.admin.server.web.rest;

import org.wappli.admin.api.rest.criteria.CustomerCriteria;
import org.wappli.admin.api.rest.dto.TestDTOFactory;
import org.wappli.admin.api.rest.dto.entities.CustomerDTO;
import org.wappli.admin.server.WappliAdminApplication;
import org.wappli.admin.server.domain.Customer;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.wappli.admin.server.repository.CustomerRepository;
import org.wappli.admin.server.service.CustomerService;
import org.wappli.admin.server.service.SinglesDB;
import org.wappli.admin.server.service.mapper.CustomerMapper;
import org.wappli.admin.server.service.query.CustomerQueryService;
import org.wappli.common.server.logging.MessageLogger;
import org.wappli.common.server.web.rest.AbstractCrudResourceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WappliAdminApplication.class)
public class CustomerResourceIntTest extends AbstractCrudResourceTest<Customer,
        CustomerDTO,
        CustomerCriteria,
        CustomerRepository,
        CustomerService,
        CustomerQueryService,
        CustomerMapper,
        CustomerResource> {

    @Autowired private CustomerRepository repository;
    @Autowired private CustomerMapper mapper;
    @Autowired private CustomerService service;
    @Autowired private CustomerQueryService queryService;
    @Autowired private MessageLogger commonLog;
    @Autowired private SinglesDB sDb;

    @Override
    protected void setServices() {
        super.repository = this.repository;
        super.mapper = this.mapper;
        super.service = this.service;
        super.queryService = this.queryService;
        super.singlesDb = this.sDb;
    }

    @Override
    protected CustomerDTO createAnotherEntityDTO() {
        CustomerDTO customerDTO = TestDTOFactory.createCustomerDTO();

        customerDTO.setName("new_name");

        return customerDTO;
    }

    @Override
    protected long getEntityId() {
        return sDb.getCustormer().getId();
    }

    @Override
    protected void assertFieldsEqualRequired(Customer testEntity) {
        assertEquals(createAnotherEntityDTO().getName(), testEntity.getName());
    }

    @Override
    protected CustomerDTO setARequiredFieldToNull(CustomerDTO entitiyDTO) {
        entitiyDTO.setName(null);

        return entitiyDTO;
    }

    @Override
    protected ResultActions assertAllJsonObjectsContainDefaultValues(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(jsonPath("$.[*].item.name").value(hasItem(TestDTOFactory.createCustomerDTO().getName())));
    }

    @Override
    protected ResultActions assertJsonObjectContainsDefaultValues(ResultActions resultActions) throws Exception {
        return resultActions.andExpect(jsonPath("$.item.name").value(TestDTOFactory.createCustomerDTO().getName()));
    }

    @Override
    protected String[] filtersForDefaultEntityShouldBeFound() {
        return new String[]{
                "name.equals=" + TestDTOFactory.createCustomerDTO().getName(),
                "name.in=" + TestDTOFactory.createCustomerDTO().getName(),
                "name.specified=true"
        };
    }

    @Override
    protected String[] filtersForDefaultEntityShouldNotBeFound() {
        return new String[]{
                "name.equals=" + createAnotherEntityDTO().getName(),
                "name.in=" + createAnotherEntityDTO().getName(),
                "name.specified=false"
        };
    }

    @Override
    protected ResultActions defaultEntityShouldBeFoundAttributeCheck(ResultActions resultActions) throws Exception {
        return resultActions.andExpect(jsonPath("$.[*].item.name").value(hasItem(TestDTOFactory.createCustomerDTO().getName())));
    }

    @Override
    protected void setFieldsToUpdated(CustomerDTO updatedEntityDTO) {
        updatedEntityDTO
                .setName(createAnotherEntityDTO().getName());
    }

    @Override
    protected void assertFieldsEqualUpdated(Customer testEntity) {
        assertThat(testEntity.getName()).isEqualTo(createAnotherEntityDTO().getName());
    }

    @Override
    protected String getEntityURL() {
        return "/customers";
    }
}
