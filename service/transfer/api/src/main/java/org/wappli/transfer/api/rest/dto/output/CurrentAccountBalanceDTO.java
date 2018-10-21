package org.wappli.transfer.api.rest.dto.output;

import org.wappli.common.api.rest.dto.entities.EntityDTO;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

public class CurrentAccountBalanceDTO implements EntityDTO {
    @NotNull
    Long accountId;

    @NotNull
    BigDecimal balance;

    @NotNull
    Instant timestamp;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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
