package org.wappli.admin.server.web.rest;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.wappli.admin.api.rest.criteria.BankAccountCriteria;
import org.wappli.admin.api.rest.dto.TestDTOFactory;
import org.wappli.admin.api.rest.dto.entities.BankAccountDTO;
import org.wappli.admin.server.WappliAdminApplication;
import org.wappli.admin.server.domain.BankAccount;
import org.wappli.admin.server.repository.BankAccountRepository;
import org.wappli.admin.server.service.BankAccountService;
import org.wappli.admin.server.service.SinglesDB;
import org.wappli.admin.server.service.mapper.BankAccountMapper;
import org.wappli.admin.server.service.query.BankAccountQueryService;
import org.wappli.common.server.logging.MessageLogger;
import org.wappli.common.server.web.rest.AbstractCrudResourceTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WappliAdminApplication.class)
public class BankAccountResourceIntTest extends AbstractCrudResourceTest<BankAccount,
        BankAccountDTO,
        BankAccountCriteria,
        BankAccountRepository,
        BankAccountService,
        BankAccountQueryService,
        BankAccountMapper,
        BankAccountResource> {

    @Autowired private BankAccountRepository repository;
    @Autowired private BankAccountMapper mapper;
    @Autowired private BankAccountService service;
    @Autowired private BankAccountQueryService queryService;
    @Autowired private MessageLogger commonLog;
    @Autowired private SinglesDB singlesDb;

    @Override
    protected void setServices() {
        super.repository = this.repository;
        super.mapper = this.mapper;
        super.service = this.service;
        super.queryService = this.queryService;
        super.singlesDb = this.singlesDb;
    }

    @Override
    protected BankAccountDTO createAnotherEntityDTO() {
        BankAccountDTO bankAccountDTO = TestDTOFactory.createBankAccountDTO();

        bankAccountDTO.setCurrency("EUR");
        bankAccountDTO.setCustomerId(singlesDb.getCustormer().getId());

        return bankAccountDTO;
    }

    @Override
    protected long getEntityId() {
        return singlesDb.getBankAccount().getId();
    }

    @Override
    protected void assertFieldsEqualRequired(BankAccount testEntity) {
        assertEquals(createAnotherEntityDTO().getCurrency(), testEntity.getCurrency());
        assertEquals(createAnotherEntityDTO().getCustomerId(), testEntity.getCustomer().getId());
    }

    @Override
    protected BankAccountDTO setARequiredFieldToNull(BankAccountDTO entitiyDTO) {
        entitiyDTO.setCurrency(null);

        return entitiyDTO;
    }

    @Override
    protected ResultActions assertAllJsonObjectsContainDefaultValues(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(jsonPath("$.[*].item.currency").value(hasItem(TestDTOFactory.createBankAccountDTO().getCurrency())));
    }

    @Override
    protected ResultActions assertJsonObjectContainsDefaultValues(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(jsonPath("$.item.currency").value(TestDTOFactory.createBankAccountDTO().getCurrency()));
    }

    @Override
    protected String[] filtersForDefaultEntityShouldBeFound() {
        return new String[]{
                "currency.equals=" + TestDTOFactory.createBankAccountDTO().getCurrency(),
                "currency.in=" + TestDTOFactory.createBankAccountDTO().getCurrency(),
                "currency.specified=true"
        };
    }

    @Override
    protected String[] filtersForDefaultEntityShouldNotBeFound() {
        return new String[]{
                "currency.equals=" + createAnotherEntityDTO().getCurrency(),
                "currency.in=" + createAnotherEntityDTO().getCurrency(),
                "currency.specified=false"
        };
    }

    @Override
    protected ResultActions defaultEntityShouldBeFoundAttributeCheck(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(jsonPath("$.[*].item.currency").value(hasItem(TestDTOFactory.createBankAccountDTO().getCurrency())));
    }

    @Override
    protected void setFieldsToUpdated(BankAccountDTO updatedEntityDTO) {
        updatedEntityDTO.setCurrency(createAnotherEntityDTO().getCurrency());

    }

    @Override
    protected void assertFieldsEqualUpdated(BankAccount testEntity) {
        assertThat(testEntity.getCurrency()).isEqualTo(createAnotherEntityDTO().getCurrency());
    }

    @Override
    protected String getEntityURL() {
        return "/bankAccounts";
    }
}
