package org.wappli.transfer.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wappli.transfer.server.domain.AccountBalance;
import org.wappli.transfer.server.domain.DepositOrWithdraw;
import org.wappli.transfer.server.repository.AccountBalanceRepository;
import org.wappli.transfer.server.repository.DepositOrWithdrawRepository;
import org.wappli.transfer.server.service.DepositOrWithdrawService;

import java.util.Optional;

@Service
@Transactional
public class DepositOrWithdrawServiceImpl implements DepositOrWithdrawService {
    private static final Logger log = LoggerFactory.getLogger(DepositOrWithdrawServiceImpl.class);

    private DepositOrWithdrawRepository depositOrWithdrawRepository;
    private AccountBalanceRepository accountBalanceRepository;

    public DepositOrWithdrawServiceImpl(DepositOrWithdrawRepository depositOrWithdrawRepository, AccountBalanceRepository accountBalanceRepository) {
        this.depositOrWithdrawRepository = depositOrWithdrawRepository;
        this.accountBalanceRepository = accountBalanceRepository;
    }

    @Override
    public DepositOrWithdraw create(DepositOrWithdraw depositOrWithdraw) {
        log.debug("Request to create : {}", depositOrWithdraw);

        Long bankAccountId = depositOrWithdraw.getBankAccountId();
        AccountBalance accountBalance = accountBalanceRepository.findById(bankAccountId).orElseThrow(() -> new ObjectNotFoundException(bankAccountId, AccountBalance.class));

        accountBalance.setBalance(accountBalance.getBalance().add(depositOrWithdraw.getAmount()));
        accountBalanceRepository.save(accountBalance);
        depositOrWithdrawRepository.save(depositOrWithdraw);

        return depositOrWithdraw;
    }
}
