package org.wappli.transfer.server.service;


import org.wappli.transfer.server.domain.AmountTransfer;
import org.wappli.transfer.server.domain.DepositOrWithdraw;

public interface TransferService {
    DepositOrWithdraw create(DepositOrWithdraw depositOrWithdraw);
    AmountTransfer create(AmountTransfer amountTransfer);
}
