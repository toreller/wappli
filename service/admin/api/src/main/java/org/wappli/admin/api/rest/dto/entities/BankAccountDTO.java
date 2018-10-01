package org.wappli.admin.api.rest.dto.entities;

import org.wappli.common.api.rest.dto.entities.EntityDTO;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BankAccountDTO implements EntityDTO {
    @NotNull
    private String currency;

    @NotNull
    Long customerId;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
