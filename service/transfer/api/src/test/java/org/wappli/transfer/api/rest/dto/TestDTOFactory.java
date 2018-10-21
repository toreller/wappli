package org.wappli.transfer.api.rest.dto;

import org.wappli.transfer.api.rest.dto.input.DepositOrWithdrawDTO;
import org.wappli.transfer.api.rest.dto.input.AmountTransferDTO;
import org.wappli.transfer.api.rest.dto.output.CurrentAccountBalanceDTO;

import java.math.BigDecimal;
import java.time.Instant;

public class TestDTOFactory {
    public static DepositOrWithdrawDTO createDepositOrWithdrawDTO() {
        DepositOrWithdrawDTO dto = new DepositOrWithdrawDTO();

        dto.setBankAccountId(1L);
        dto.setAmount(BigDecimal.valueOf(123456L));
        dto.setRemark("deposit amount");

        return dto;
    }

    public static DepositOrWithdrawDTO createAnotherDepositOrWithdrawDTO() {
        DepositOrWithdrawDTO dto = new DepositOrWithdrawDTO();

        dto.setBankAccountId(1L);
        dto.setAmount(BigDecimal.valueOf(-654321L));
        dto.setRemark("withdraw amount");

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

    public static AmountTransferDTO createAnotherAmountTransferDTO() {
        AmountTransferDTO dto = new AmountTransferDTO();

        dto.setSourceBankAccountId(2L);
        dto.setTargetBankAccountId(1L);
        dto.setAmount(BigDecimal.valueOf(4321L));
        dto.setRemark("another trasfer amount");

        return dto;
    }

    public static CurrentAccountBalanceDTO createCurrentAccountBalanceDTO() {
        CurrentAccountBalanceDTO dto = new CurrentAccountBalanceDTO();

        dto.setAccountId(1L);
        dto.setBalance(BigDecimal.valueOf(3224.0));
        dto.setTimestamp(Instant.parse("2018-09-20T12:34:56"));

        return dto;
    }

}
