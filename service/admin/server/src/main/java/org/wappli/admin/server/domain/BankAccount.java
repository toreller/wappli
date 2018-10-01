package org.wappli.admin.server.domain;

import org.wappli.common.api.rest.util.HasId;
import org.wappli.common.server.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class BankAccount extends AbstractEntity implements HasId, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SEQ_NAME = "seq_bank_account_id";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private BigDecimal balance;

    @NotNull
    @Column(nullable = false, length = 3)
    private String currency;

    @NotNull
    @ManyToOne(optional = false)
    Customer customer;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BankAccount customer(Customer customer) {
        this.currency = currency;

        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
