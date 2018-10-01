package org.wappli.admin.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wappli.admin.server.domain.BankAccount;
import org.wappli.admin.server.domain.Customer;
import org.wappli.admin.server.repository.BankAccountRepository;
import org.wappli.admin.server.repository.CustomerRepository;
import org.wappli.common.server.service.AbstractCrudService;
import org.wappli.admin.server.service.CustomerService;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl extends AbstractCrudService<Customer, CustomerRepository> implements CustomerService{
    private final BankAccountRepository bankAccountRepository;
    private final EntityManager em;


    public CustomerServiceImpl(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, EntityManager entityManager) {
        super(customerRepository);

        this.bankAccountRepository = bankAccountRepository;
        this.em = entityManager;
    }

    @Override
    public void delete(Customer entity) {
        List<BankAccount> bankAccounts = bankAccountRepository.findByCustomerId(entity.getId());

        em.createQuery("delete from BankAccount where customer_id = :customerId")
                .setParameter("customerId", entity.getId()).executeUpdate();

        for (BankAccount bankAccount : bankAccounts) {
            em.detach(bankAccount);
        }

        super.delete(entity);
    }
}
