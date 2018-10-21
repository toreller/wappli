package org.wappli.transfer.server.web.rest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.wappli.common.server.config.Constants;
import org.wappli.common.server.service.AbstractSinglesDb;
import org.wappli.common.server.web.rest.TestUtil;
import org.wappli.transfer.api.rest.dto.TestDTOFactory;
import org.wappli.transfer.api.rest.dto.input.AmountTransferDTO;
import org.wappli.transfer.api.rest.dto.input.DepositOrWithdrawDTO;
import org.wappli.transfer.api.rest.interfaces.TransferWebInterface;
import org.wappli.transfer.server.WappliTransferApplication;
import org.wappli.transfer.server.domain.AmountTransfer;
import org.wappli.transfer.server.domain.DepositOrWithdraw;
import org.wappli.transfer.server.factory.TestObjectFactory;
import org.wappli.transfer.server.repository.AmountTransferRepository;
import org.wappli.transfer.server.repository.DepositOrWithdrawRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WappliTransferApplication.class)
@AutoConfigureMockMvc
public class TransferResourceIntTest {
    public static final String URL = Constants.BE_API_V + TransferWebInterface.ENTITY_URL;
    @Autowired
    protected AbstractSinglesDb singlesDb;

    @Autowired
    protected MockMvc restMockMvc;

    @Autowired
    DepositOrWithdrawRepository depositOrWithdrawRepository;

    @Autowired
    AmountTransferRepository amountTransferRepository;

    @Before
    public void setup() {
        singlesDb.create();
    }

    @After
    public void purgeDb() {
        singlesDb.truncateAll();
    }


    @Test
    @Transactional
    public void depositOrWithdraw() throws Exception {
        int databaseSizeBeforeCreate = depositOrWithdrawRepository.findAll().size();

        DepositOrWithdrawDTO dto = TestDTOFactory.createAnotherDepositOrWithdrawDTO();

        String contentAsString = restMockMvc.perform(post(URL + "/deposit-or-withdraw")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        List<DepositOrWithdraw> entityList = depositOrWithdrawRepository.findAll();

        assertThat(entityList).hasSize(databaseSizeBeforeCreate + 1);

        DepositOrWithdraw testEntity = entityList.get(entityList.size() - 1);

        assertEquals(TestDTOFactory.createAnotherDepositOrWithdrawDTO().getAmount(), testEntity.getAmount());
        assertEquals(TestDTOFactory.createAnotherDepositOrWithdrawDTO().getRemark(), testEntity.getRemark());
        JSONAssert.assertEquals("{id: " + testEntity.getId() + "}", contentAsString, JSONCompareMode.STRICT);
    }

    @Test
    @Transactional
    public void transfer() throws Exception {
        int databaseSizeBeforeCreate = amountTransferRepository.findAll().size();
        AmountTransferDTO dto = TestDTOFactory.createAnotherAmountTransferDTO();

        String contentAsString = restMockMvc.perform(post(URL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        List<AmountTransfer> entityList = amountTransferRepository.findAll();

        assertThat(entityList).hasSize(databaseSizeBeforeCreate + 1);

        AmountTransfer testEntity = entityList.get(entityList.size() - 1);

        assertEquals(TestDTOFactory.createAnotherAmountTransferDTO().getAmount(), testEntity.getFrom().getAmount());
        assertEquals(TestDTOFactory.createAnotherAmountTransferDTO().getRemark(), testEntity.getFrom().getRemark());
        assertEquals(TestDTOFactory.createAnotherAmountTransferDTO().getSourceBankAccountId(), testEntity.getFrom().getBankAccountId());
        assertEquals(TestDTOFactory.createAnotherAmountTransferDTO().getTargetBankAccountId(), testEntity.getTo().getBankAccountId());
        JSONAssert.assertEquals("{id: " + testEntity.getId() + "}", contentAsString, JSONCompareMode.STRICT);
    }

    @Test
    @Transactional
    public void getCurrentAccountBalance() throws Exception {
        ResultActions resultActions = restMockMvc.perform(get(URL + "/account-balance/{accountId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.accountId").value(1L))
                .andExpect(jsonPath("$.balance").value(TestObjectFactory.createAccountBalance().getBalance()))
                .andExpect(jsonPath("$.timestamp").value(TestObjectFactory.createAccountBalance().getTimestamp().toString()));
    }


    @Test
    @Transactional
    public void getAllDepositOrWithdraws() throws Exception {
        restMockMvc.perform(get(URL + "/deposit-or-withdraws/{accountId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].bankAccountId").value(hasItem(TestObjectFactory.createDepositOrWithdraw().getBankAccountId().intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(TestObjectFactory.createDepositOrWithdraw().getAmount().multiply(BigDecimal.valueOf(-1L)).doubleValue())))
                .andExpect(jsonPath("$.[*].remark").value(hasItem(TestObjectFactory.createDepositOrWithdraw().getRemark())));
    }
}
