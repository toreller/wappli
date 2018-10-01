package org.wappli.admin.api.rest.criteria;

import org.wappli.common.api.rest.filter.BigDecimalFilter;
import org.wappli.common.api.rest.filter.LongFilter;
import org.wappli.common.api.rest.filter.StringFilter;

import java.io.Serializable;

public class BankAccountCriteria implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter balance;

    private StringFilter currency;

    public BankAccountCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getBalance() {
        return balance;
    }

    public void setBalance(BigDecimalFilter balance) {
        this.balance = balance;
    }

    public StringFilter getCurrency() {
        return currency;
    }

    public void setCurrency(StringFilter currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "BankAccountCriteria{" +
                "id=" + id +
                ", balance=" + balance +
                ", currency=" + currency +
                '}';
    }
}
