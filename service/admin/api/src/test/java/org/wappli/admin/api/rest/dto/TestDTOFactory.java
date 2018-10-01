package org.wappli.admin.api.rest.dto;

import org.wappli.admin.api.rest.dto.entities.BankAccountDTO;
import org.wappli.admin.api.rest.dto.entities.CustomerDTO;

import java.math.BigDecimal;

public class TestDTOFactory {
    public static CustomerDTO createCustomerDTO() {
        CustomerDTO dto = new CustomerDTO();

        dto.setName("customer_name");

        return dto;
    }

    public static BankAccountDTO createBankAccountDTO() {
        BankAccountDTO dto = new BankAccountDTO();

        dto.setCurrency("HUF");
        dto.setCustomerId(1L);

        return dto;
    }
}
