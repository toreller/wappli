package org.wappli.transfer.server.factory;


import org.wappli.transfer.server.domain.AccountBalance;
import org.wappli.transfer.server.domain.AmountTransfer;
import org.wappli.transfer.server.domain.DepositOrWithdraw;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

public class TestObjectFactory {
    public static DepositOrWithdraw createDepositOrWithdraw() {
        DepositOrWithdraw o = new DepositOrWithdraw();

        o.setBankAccountId(1L);
        o.setAmount(BigDecimal.valueOf(123.3));
        o.setRemark("deposit");

        return o;
    }

    public static AmountTransfer createAmountTransfer(DepositOrWithdraw from, DepositOrWithdraw to) {
        AmountTransfer o = new AmountTransfer();

        o.setFrom(from);
        o.setTo(to);

        return o;
    }

    public static AccountBalance createAccountBalance() {
        AccountBalance o = new AccountBalance();

        o.setBalance(BigDecimal.valueOf(1999.22));
        o.setTimestamp(Instant.parse("2018-07-03T21:33:00"));

        return o;
    }
}
