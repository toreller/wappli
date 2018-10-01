package org.wappli.admin.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wappli.admin.server.domain.BankAccount;
import org.wappli.admin.server.repository.BankAccountRepository;
import org.wappli.common.server.service.AbstractCrudService;
import org.wappli.admin.server.service.BankAccountService;

@Service
@Transactional
public class BankAccountServiceImpl extends AbstractCrudService<BankAccount, BankAccountRepository> implements BankAccountService{
    public BankAccountServiceImpl(BankAccountRepository BankAccountRepository) {
        super(BankAccountRepository);
    }
}
