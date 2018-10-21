package org.wappli.admin.server.service;

import org.hibernate.Session;
import org.wappli.admin.server.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.wappli.admin.server.factory.TestObjectFactory;
import org.wappli.common.server.service.AbstractSinglesDb;

import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class SinglesDB extends AbstractSinglesDb {

    @Autowired
    private EntityManager em;

    private BankAccount bankAccount;
    private Customer custormer;

    @Override
    @Transactional
    public void create() {
        custormer = TestObjectFactory.createCustomer();
        em.persist(custormer);

        bankAccount = TestObjectFactory.createBankAccount(custormer);
        em.persist(bankAccount);

        em.flush();
    }

    @Override
    protected void detachObjects() {
        em.detach(bankAccount);
        em.detach(custormer);
        bankAccount = null;
        custormer = null;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public Customer getCustormer() {
        return custormer;
    }
}
