package org.wappli.transfer.server.domain;

import org.wappli.transfer.api.rest.util.HasId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
public class AccountBalance implements HasId, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Column(nullable = false)
    BigDecimal balance;

    @NotNull
    Instant timestamp;

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

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
