package org.wappli.transfer.server.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wappli.admin.api.rest.dto.entities.BankAccountDTO;
import org.wappli.admin.api.rest.interfaces.BankAccountWebInterface;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.server.web.util.ResponseUtil;
import org.wappli.common.server.web.util.URIUtil;
import org.wappli.transfer.api.rest.dto.input.DepositOrWithdrawDTO;
import org.wappli.transfer.api.rest.dto.input.AmountTransferDTO;
import org.wappli.transfer.api.rest.dto.output.CurrentAccountBalanceDTO;
import org.wappli.transfer.api.rest.dto.output.IdDTO;
import org.wappli.transfer.api.rest.interfaces.TransferWebInterface;
import org.wappli.transfer.server.domain.AmountTransfer;
import org.wappli.transfer.server.domain.DepositOrWithdraw;
import org.wappli.transfer.server.service.TransferService;
import org.wappli.transfer.server.service.mapper.AmountTransferMapper;
import org.wappli.transfer.server.service.mapper.DepositOrWithdrawMapper;

import java.util.List;

import static org.wappli.common.server.config.Constants.BE_API_V;

@RestController
public class TransferResource implements TransferWebInterface {
    private static final Logger log = LoggerFactory.getLogger(TransferResource.class);
    public static final String DEPOSIT_OR_WITHDRAW_ENTITY_URL = ENTITY_URL + "/deposit-or-withdraw";

    private BankAccountWebInterface bankAccountRemote;
    private TransferService service;
    private DepositOrWithdrawMapper depositOrWithdrawMapper;
    private AmountTransferMapper amountTransferMapper;

    public TransferResource(BankAccountWebInterface bankAccountRemote, TransferService service,
                            DepositOrWithdrawMapper depositOrWithdrawMapper,
                            AmountTransferMapper amountTransferMapper) {
        this.bankAccountRemote = bankAccountRemote;
        this.service = service;
        this.depositOrWithdrawMapper = depositOrWithdrawMapper;
        this.amountTransferMapper = amountTransferMapper;
    }

    @Override
    @PostMapping(DEPOSIT_OR_WITHDRAW_ENTITY_URL)
    public ResponseEntity<IdDTO> depositOrWithdraw(@RequestBody DepositOrWithdrawDTO depositOrWithdrawDTO) {
        // todo move to facade microservice
        // checkBankAccountExists(depositOrWithdrawDTO);

        DepositOrWithdraw entity = service.create(depositOrWithdrawMapper.toEntity(depositOrWithdrawDTO));
        long id = entity.getId();

        return ResponseEntity.created(URIUtil.createURI(BE_API_V + DEPOSIT_OR_WITHDRAW_ENTITY_URL + "/" + id))
                .body(new IdDTO().id(id));
    }

    private void checkBankAccountExists(@RequestBody DepositOrWithdrawDTO depositOrWithdrawDTO) {
        ResponseEntity<EntityWithIdOutputDTO<BankAccountDTO>> bankAccountResponse = bankAccountRemote.get(depositOrWithdrawDTO.getBankAccountId());

        ResponseUtil.unwrapOrException(log, bankAccountResponse);
    }

    @Override
    @PostMapping(ENTITY_URL)
    public ResponseEntity<IdDTO> transfer(AmountTransferDTO amountTransferDTO) {
        AmountTransfer entity = service.create(amountTransferMapper.toEntity(amountTransferDTO));
        long id = entity.getId();

        return ResponseEntity.created(URIUtil.createURI(BE_API_V + ENTITY_URL + "/" + id))
                .body(new IdDTO().id(id));
    }

    @Override
    @GetMapping(ENTITY_URL + "/account-balance/{id}")
    public ResponseEntity<CurrentAccountBalanceDTO> getCurrentAccountBalance(@PathVariable("id") Long accountId) {
        return null;
    }

    @Override
    @GetMapping(ENTITY_URL + "/deposit-or-withdraws")
    public ResponseEntity<List<DepositOrWithdrawDTO>> getAllDepositOrWithdraws(@PathVariable("id") Long accountId) {
        return null;
    }
}
