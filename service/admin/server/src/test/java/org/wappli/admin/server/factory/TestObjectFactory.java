package org.wappli.admin.server.factory;


import org.wappli.admin.server.domain.BankAccount;
import org.wappli.admin.server.domain.Customer;

import java.math.BigDecimal;

public class TestObjectFactory {
    public static Customer createCustomer() {
        Customer o = new Customer();

        o.setName("customer_name");

        return o;
    }

    public static BankAccount createBankAccount(Customer customer) {
        BankAccount o = new BankAccount();

        o.setCurrency("HUF");
        o.setCustomer(customer);

        return o;
    }

    public static BankAccount createAnotherBankAccount(Customer customer) {
        BankAccount o = new BankAccount();

        o.setCurrency("EUR");
        o.setCustomer(customer);

        return o;
    }
}
