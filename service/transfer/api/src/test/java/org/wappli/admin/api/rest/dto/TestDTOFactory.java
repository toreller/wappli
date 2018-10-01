package org.wappli.admin.api.rest.dto;

import org.wappli.transfer.api.rest.dto.input.DepositOrWithdrawDTO;
import org.wappli.transfer.api.rest.dto.input.AmountTransferDTO;

import java.math.BigDecimal;

public class TestDTOFactory {
    public static DepositOrWithdrawDTO createAmountAddDTO() {
        DepositOrWithdrawDTO dto = new DepositOrWithdrawDTO();

        dto.setBankAccountId(1L);
        dto.setAmount(BigDecimal.valueOf(123456L));
        dto.setRemark("deposit amount");

        return dto;
    }

    public static AmountTransferDTO createAmountTransferDTO() {
        AmountTransferDTO dto = new AmountTransferDTO();

        dto.setSourceBankAccountId(1L);
        dto.setTargetBankAccountId(2L);
        dto.setAmount(BigDecimal.valueOf(4321L));
        dto.setRemark("trasfer amount");

        return dto;
    }

}
