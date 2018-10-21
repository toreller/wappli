package org.wappli.transfer.server.domain;

import org.wappli.transfer.api.rest.util.HasId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class DepositOrWithdraw implements HasId, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SEQ_NAME = "seq_deposit_or_withdraw_id";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @NotNull
    @Column(nullable = false)
    Long bankAccountId;

    @NotNull
    @Column(nullable = false)
    BigDecimal amount;

    @NotNull
    @Column(nullable = false)
    String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
