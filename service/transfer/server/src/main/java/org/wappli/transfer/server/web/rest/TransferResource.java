package org.wappli.transfer.server.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wappli.common.server.web.util.URIUtil;
import org.wappli.transfer.api.rest.dto.input.DepositOrWithdrawDTO;
import org.wappli.transfer.api.rest.dto.input.AmountTransferDTO;
import org.wappli.transfer.api.rest.dto.output.CurrentAccountBalanceDTO;
import org.wappli.transfer.api.rest.dto.output.IdDTO;
import org.wappli.transfer.api.rest.interfaces.TransferWebInterface;
import org.wappli.transfer.server.domain.AccountBalance;
import org.wappli.transfer.server.domain.AmountTransfer;
import org.wappli.transfer.server.domain.DepositOrWithdraw;
import org.wappli.transfer.server.service.TransferService;
import org.wappli.transfer.server.service.mapper.AccountBalanceMapper;
import org.wappli.transfer.server.service.mapper.AmountTransferMapper;
import org.wappli.transfer.server.service.mapper.DepositOrWithdrawMapper;

import java.util.List;
import java.util.stream.Collectors;

import static org.wappli.common.server.config.Constants.BE_API_V;

@RestController
public class TransferResource implements TransferWebInterface {
    private static final Logger log = LoggerFactory.getLogger(TransferResource.class);
    public static final String URL = BE_API_V + ENTITY_URL;
    public static final String DEPOSIT_OR_WITHDRAW_URL = URL + "/deposit-or-withdraw";

    private TransferService service;
    private DepositOrWithdrawMapper depositOrWithdrawMapper;
    private AmountTransferMapper amountTransferMapper;
    private AccountBalanceMapper accountBalanceMapper;

    public TransferResource(TransferService service,
                            DepositOrWithdrawMapper depositOrWithdrawMapper,
                            AmountTransferMapper amountTransferMapper,
                            AccountBalanceMapper accountBalanceMapper) {
        this.service = service;
        this.depositOrWithdrawMapper = depositOrWithdrawMapper;
        this.amountTransferMapper = amountTransferMapper;
        this.accountBalanceMapper = accountBalanceMapper;
    }

    @Override
    @PostMapping(DEPOSIT_OR_WITHDRAW_URL)
    public ResponseEntity<IdDTO> depositOrWithdraw(@RequestBody DepositOrWithdrawDTO depositOrWithdrawDTO) {
        // todo move to facade microservice
        // checkBankAccountExists(depositOrWithdrawDTO);

        DepositOrWithdraw entity = service.create(depositOrWithdrawMapper.toEntity(depositOrWithdrawDTO));
        long id = entity.getId();

        return ResponseEntity.created(URIUtil.createURI(DEPOSIT_OR_WITHDRAW_URL + "/" + id))
                .body(new IdDTO().id(id));
    }

//    private void checkBankAccountExists(@RequestBody DepositOrWithdrawDTO depositOrWithdrawDTO) {
//        ResponseEntity<EntityWithIdOutputDTO<BankAccountDTO>> bankAccountResponse = bankAccountRemote.get(depositOrWithdrawDTO.getBankAccountId());
//
//        ResponseUtil.unwrapOrException(log, bankAccountResponse);
//    }

    @Override
    @PostMapping(URL)
    public ResponseEntity<IdDTO> transfer(@RequestBody AmountTransferDTO amountTransferDTO) {
        AmountTransfer entity = service.create(amountTransferMapper.toEntity(amountTransferDTO));
        long id = entity.getId();

        return ResponseEntity.created(URIUtil.createURI(URL + "/" + id))
                .body(new IdDTO().id(id));
    }

    @Override
    @GetMapping(URL + "/account-balance/{accountId}")
    public ResponseEntity<CurrentAccountBalanceDTO> getCurrentAccountBalance(@PathVariable("accountId") Long accountId) {
        AccountBalance accountBalance = service.getAccountBalance(accountId);

        return ResponseEntity.ok(accountBalanceMapper.toDto(accountBalance));
    }

    @Override
    @GetMapping(URL + "/deposit-or-withdraws/{accountId}")
    public ResponseEntity<List<DepositOrWithdrawDTO>> getAllDepositOrWithdraws(@PathVariable("accountId") Long accountId) {
        return ResponseEntity.ok(service.getAllDepositOrWithdraws(accountId).stream()
                .map(e -> depositOrWithdrawMapper.toDto(e)).collect(Collectors.toList()));
    }
}
