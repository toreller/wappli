package org.wappli.transfer.api.rest.interfaces;


import org.springframework.http.ResponseEntity;
import org.wappli.transfer.api.rest.dto.input.DepositOrWithdrawDTO;
import org.wappli.transfer.api.rest.dto.input.AmountTransferDTO;
import org.wappli.transfer.api.rest.dto.output.CurrentAccountBalanceDTO;
import org.wappli.transfer.api.rest.dto.output.IdDTO;

import java.util.List;

public interface TransferInterface {
    ResponseEntity<IdDTO> depositOrWithdraw(DepositOrWithdrawDTO amountInOut);
    ResponseEntity<IdDTO> transfer(AmountTransferDTO amountTransfer);
    ResponseEntity<CurrentAccountBalanceDTO> getCurrentAccountBalance(Long accountId);
    ResponseEntity<List<DepositOrWithdrawDTO>> getAllDepositOrWithdraws(Long accountId);
}
