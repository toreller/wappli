package org.wappli.transfer.server.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.server.service.mapper.MapperUsingRepository;
import org.wappli.transfer.api.rest.dto.input.DepositOrWithdrawDTO;
import org.wappli.transfer.server.domain.DepositOrWithdraw;
import org.wappli.transfer.server.repository.DepositOrWithdrawRepository;

@Mapper(componentModel = "spring")
public abstract class DepositOrWithdrawMapper extends MapperUsingRepository<DepositOrWithdrawDTO, DepositOrWithdraw> {
    @Autowired
    private DepositOrWithdrawRepository repository;

    @Override
    @Mapping(source = "bankAccountId", target = "item.bankAccountId")
    @Mapping(source = "amount", target = "item.amount")
    @Mapping(source = "remark", target = "item.remark")
    public abstract EntityWithIdOutputDTO<DepositOrWithdrawDTO> toDtoWithId(DepositOrWithdraw customer);

    @Override
    public abstract DepositOrWithdrawDTO toDto(DepositOrWithdraw customer);

    @Override
    @Mapping(target = "id", ignore = true)
    public abstract void merge(DepositOrWithdrawDTO customerDTO, @MappingTarget DepositOrWithdraw entity);

    @Override
    @Mapping(target = "id", ignore = true)
    public abstract DepositOrWithdraw toEntity(DepositOrWithdrawDTO customerDTO);

    @Override
    protected JpaRepository<DepositOrWithdraw, Long> getRepository() {
        return repository;
    }
}
