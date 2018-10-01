package org.wappli.transfer.server.domain;

import org.wappli.transfer.api.rest.util.HasId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class AccountBalance implements HasId, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SEQ_NAME = "seq_account_balance_id";

    @Id
    private Long id;

    @NotNull
    @Column(nullable = false)
    BigDecimal balance;

    @NotNull
    LocalDateTime timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
