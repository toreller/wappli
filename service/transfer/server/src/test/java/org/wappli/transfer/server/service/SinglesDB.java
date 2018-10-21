package org.wappli.transfer.server.service;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.wappli.common.server.service.AbstractSinglesDb;
import org.wappli.transfer.server.domain.AccountBalance;
import org.wappli.transfer.server.domain.AmountTransfer;
import org.wappli.transfer.server.domain.DepositOrWithdraw;
import org.wappli.transfer.server.factory.TestObjectFactory;

import java.math.BigDecimal;

public class SinglesDB extends AbstractSinglesDb {

    private AccountBalance accountBalanceFrom;
    private AccountBalance accountBalanceTo;
    private AmountTransfer amountTransfer;
    private DepositOrWithdraw depositOrWithdrawFrom;
    private DepositOrWithdraw depositOrWithdrawTo;

    @Override
    @Transactional
    public void create() {
        depositOrWithdrawFrom = TestObjectFactory.createDepositOrWithdraw();
        BigDecimal transferAmount = depositOrWithdrawFrom.getAmount();

        depositOrWithdrawFrom.setAmount(transferAmount.multiply(BigDecimal.valueOf(-1L)));
        em.persist(depositOrWithdrawFrom);

        depositOrWithdrawTo = TestObjectFactory.createDepositOrWithdraw();
        long bankAccountIdTo = depositOrWithdrawFrom.getBankAccountId() + 1L;
        depositOrWithdrawTo.setBankAccountId(bankAccountIdTo);
        depositOrWithdrawTo.setAmount(transferAmount);
        em.persist(depositOrWithdrawTo);

        amountTransfer = TestObjectFactory.createAmountTransfer(depositOrWithdrawFrom, depositOrWithdrawTo);
        em.persist(amountTransfer);

        accountBalanceFrom = TestObjectFactory.createAccountBalance();
        em.persist(accountBalanceFrom);

        accountBalanceTo = TestObjectFactory.createAccountBalance();
        accountBalanceTo.setId(bankAccountIdTo);
        em.persist(accountBalanceTo);

        em.flush();
    }

    @Override
    protected void detachObjects() {
        em.detach(accountBalanceFrom);
        em.detach(accountBalanceTo);
        em.detach(amountTransfer);
        em.detach(depositOrWithdrawFrom);
        em.detach(depositOrWithdrawTo);
        accountBalanceFrom = null;
        accountBalanceTo = null;
        amountTransfer = null;
        depositOrWithdrawFrom = null;
        depositOrWithdrawTo = null;
    }

    public AccountBalance getAccountBalanceFrom() {
        return accountBalanceFrom;
    }

    public AccountBalance getAccountBalanceTo() {
        return accountBalanceTo;
    }

    public AmountTransfer getAmountTransfer() {
        return amountTransfer;
    }

    public DepositOrWithdraw getDepositOrWithdrawFrom() {
        return depositOrWithdrawFrom;
    }

    public DepositOrWithdraw getDepositOrWithdrawTo() {
        return depositOrWithdrawTo;
    }
}
