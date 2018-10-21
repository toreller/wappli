package org.wappli.transfer.server.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.server.service.mapper.MapperUsingRepository;
import org.wappli.transfer.api.rest.dto.input.AmountTransferDTO;
import org.wappli.transfer.api.rest.dto.output.CurrentAccountBalanceDTO;
import org.wappli.transfer.server.domain.AccountBalance;
import org.wappli.transfer.server.domain.AmountTransfer;
import org.wappli.transfer.server.repository.AmountTransferRepository;

@Mapper(componentModel = "spring")
public abstract class AccountBalanceMapper {

    @Mapping(source = "id", target = "accountId")
    public abstract CurrentAccountBalanceDTO toDto(AccountBalance accountBalance);

}
