package org.wappli.transfer.api.rest.dto.input;

import org.wappli.common.api.rest.dto.entities.EntityDTO;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DepositOrWithdrawDTO implements EntityDTO {
    @NotNull
    Long bankAccountId;

    @NotNull
    BigDecimal amount;

    @NotNull
    String remark;

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
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
