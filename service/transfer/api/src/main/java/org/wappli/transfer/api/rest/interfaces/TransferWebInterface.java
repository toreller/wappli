package org.wappli.transfer.api.rest.interfaces;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.wappli.transfer.api.rest.dto.input.DepositOrWithdrawDTO;
import org.wappli.transfer.api.rest.dto.input.AmountTransferDTO;
import org.wappli.transfer.api.rest.dto.output.CurrentAccountBalanceDTO;
import org.wappli.transfer.api.rest.dto.output.IdDTO;

import java.util.List;

public interface TransferWebInterface {
    String ENTITY_URL = "/transfer";

    @PostMapping(ENTITY_URL + "/deposit-or-withdraw")
    ResponseEntity<IdDTO> depositOrWithdraw(@RequestBody DepositOrWithdrawDTO depositOrWithdrawDTO);

    @PostMapping(ENTITY_URL)
    ResponseEntity<IdDTO> transfer(@RequestBody AmountTransferDTO amountTransfer);

    @GetMapping(ENTITY_URL + "/account-balance/{accountId}")
    ResponseEntity<CurrentAccountBalanceDTO> getCurrentAccountBalance(@PathVariable("accountId") Long accountId);

    @GetMapping(ENTITY_URL + "/deposit-or-withdraws/{accountId}")
    ResponseEntity<List<DepositOrWithdrawDTO>> getAllDepositOrWithdraws(@PathVariable("accountId") Long accountId);
}
