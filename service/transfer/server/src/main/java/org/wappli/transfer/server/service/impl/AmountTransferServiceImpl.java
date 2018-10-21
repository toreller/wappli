package org.wappli.transfer.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wappli.transfer.server.domain.AmountTransfer;
import org.wappli.transfer.server.repository.AmountTransferRepository;
import org.wappli.transfer.server.service.AmountTransferService;

@Service
@Transactional
public class AmountTransferServiceImpl implements AmountTransferService {
    private static final Logger log = LoggerFactory.getLogger(AmountTransferServiceImpl.class);

    private AmountTransferRepository depositOrWithdrawRepository;

    public AmountTransferServiceImpl(AmountTransferRepository depositOrWithdrawRepository) {
        this.depositOrWithdrawRepository = depositOrWithdrawRepository;
    }

    public void create(AmountTransfer amountTransfer) {
        log.debug("Request to save : {}", amountTransfer);

        this.depositOrWithdrawRepository.save(amountTransfer);
    }
}
