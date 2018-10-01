package org.wappli.transfer.api.rest.dto.input;

import org.wappli.common.api.rest.dto.entities.EntityDTO;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AmountTransferDTO implements EntityDTO {
    @NotNull
    Long sourceBankAccountId;

    @NotNull
    Long targetBankAccountId;

    @NotNull
    BigDecimal amount;

    @NotNull
    String remark;

    public Long getSourceBankAccountId() {
        return sourceBankAccountId;
    }

    public void setSourceBankAccountId(Long sourceBankAccountId) {
        this.sourceBankAccountId = sourceBankAccountId;
    }

    public Long getTargetBankAccountId() {
        return targetBankAccountId;
    }

    public void setTargetBankAccountId(Long targetBankAccountId) {
        this.targetBankAccountId = targetBankAccountId;
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
