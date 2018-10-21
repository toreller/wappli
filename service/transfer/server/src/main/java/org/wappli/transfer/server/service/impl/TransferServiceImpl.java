package org.wappli.transfer.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wappli.transfer.api.rest.dto.input.DepositOrWithdrawDTO;
import org.wappli.transfer.server.domain.AccountBalance;
import org.wappli.transfer.server.domain.AmountTransfer;
import org.wappli.transfer.server.domain.DepositOrWithdraw;
import org.wappli.transfer.server.repository.AccountBalanceRepository;
import org.wappli.transfer.server.repository.AmountTransferRepository;
import org.wappli.transfer.server.repository.DepositOrWithdrawRepository;
import org.wappli.transfer.server.service.TransferService;

import java.util.List;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {
    private static final Logger log = LoggerFactory.getLogger(TransferServiceImpl.class);

    private DepositOrWithdrawRepository depositOrWithdrawRepository;
    private AmountTransferRepository amountTransferRepository;
    private AccountBalanceRepository accountBalanceRepository;

    public TransferServiceImpl(DepositOrWithdrawRepository depositOrWithdrawRepository,
                               AmountTransferRepository amountTransferRepository, AccountBalanceRepository accountBalanceRepository) {
        this.depositOrWithdrawRepository = depositOrWithdrawRepository;
        this.amountTransferRepository = amountTransferRepository;
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

    @Override
    public AmountTransfer create(AmountTransfer amountTransfer) {
        log.debug("Request to create : {}", amountTransfer);

        create(amountTransfer.getFrom());
        create(amountTransfer.getTo());

        return amountTransferRepository.save(amountTransfer);
    }

    @Override
    public AccountBalance getAccountBalance(long id) {
        log.debug("Request to get accountBalance: {}", id);

        return accountBalanceRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, AccountBalance.class));
    }

    @Override
    public List<DepositOrWithdraw> getAllDepositOrWithdraws(long accountId) {
        log.debug("Request to getAll depositOrWithdraws");

        return depositOrWithdrawRepository.findByBankAccountId(accountId);
    }
}
