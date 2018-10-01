package org.wappli.admin.server.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.wappli.admin.api.rest.dto.entities.BankAccountDTO;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.admin.server.domain.BankAccount;
import org.wappli.admin.server.repository.BankAccountRepository;
import org.wappli.common.server.service.mapper.MapperUsingRepository;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public abstract class BankAccountMapper extends MapperUsingRepository<BankAccountDTO, BankAccount> {

    @Autowired
    private BankAccountRepository repository;

    @Override
    @Mapping(source = "balance", target = "item.balance")
    @Mapping(source = "currency", target = "item.currency")
    @Mapping(source = "customer.id", target = "item.customerId")
    public abstract EntityWithIdOutputDTO<BankAccountDTO> toDtoWithId(BankAccount bankAccount);

    @Override
    @Mapping(source = "id", target = "customerId")
    public abstract BankAccountDTO toDto(BankAccount bankAccount);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", source = "customerId")
    public abstract BankAccount toEntity(BankAccountDTO bankAccountDTO);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", source = "customerId")
    public abstract void merge(BankAccountDTO bankAccountDTO, @MappingTarget BankAccount entity);

    @Override
    protected JpaRepository<BankAccount, Long> getRepository() {
        return repository;
    }
}
