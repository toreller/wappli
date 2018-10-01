package org.wappli.admin.server.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.wappli.admin.server.WappliAdminApplication;
import org.wappli.admin.server.domain.BankAccount;
import org.wappli.admin.server.domain.Customer;
import org.wappli.admin.server.factory.TestObjectFactory;
import org.wappli.admin.server.repository.BankAccountRepository;
import org.wappli.admin.server.service.CustomerService;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WappliAdminApplication.class)
public class CustomerServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    @Transactional
    public void delete() {
        Statistics statistics = getStatistics();
        Customer customer = prepareDb(20);

        customerService.delete(customer);

        em.flush();

        assertEquals(1, statistics.getEntityDeleteCount());
        assertNull(em.find(Customer.class, customer.getId()));
        assertEquals(0, bankAccountRepository.findByCustomerId(customer.getId()).size());
    }

    private Statistics getStatistics() {
        Session session = em.unwrap(Session.class);
        SessionFactory sessionFactory = session.getSessionFactory();
        Statistics statistics = sessionFactory.getStatistics();

        statistics.clear();

        return statistics;
    }

    private Customer prepareDb(int noBankAccounts) {
        Customer customer = TestObjectFactory.createCustomer();

        em.persist(customer);

        Long bankAccountId = -1L;
        Set<BankAccount> bankAccounts = new HashSet<>();
        for (int i = 0; i < noBankAccounts; i++) {
            BankAccount bankAccount = TestObjectFactory.createBankAccount(customer);

            em.persist(bankAccount);
            if (bankAccountId < 0L) {
                bankAccountId = bankAccount.getId();
            }
        }

        em.flush();
        return customer;
    }
}