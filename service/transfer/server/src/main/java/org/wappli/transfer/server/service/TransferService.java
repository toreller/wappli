package org.wappli.transfer.server.service;


import org.wappli.transfer.server.domain.AccountBalance;
import org.wappli.transfer.server.domain.AmountTransfer;
import org.wappli.transfer.server.domain.DepositOrWithdraw;

import java.util.List;

public interface TransferService {
    DepositOrWithdraw create(DepositOrWithdraw depositOrWithdraw);
    AmountTransfer create(AmountTransfer amountTransfer);
    AccountBalance getAccountBalance(long id);
    List<DepositOrWithdraw> getAllDepositOrWithdraws(long accountId);
}
