package org.wappli.transfer.server.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.server.service.mapper.MapperUsingRepository;
import org.wappli.transfer.api.rest.dto.input.AmountTransferDTO;
import org.wappli.transfer.server.domain.AmountTransfer;
import org.wappli.transfer.server.repository.AmountTransferRepository;

@Mapper(componentModel = "spring")
public abstract class AmountTransferMapper extends MapperUsingRepository<AmountTransferDTO, AmountTransfer> {
    @Autowired
    private AmountTransferRepository repository;

    @Override
    @Mapping(source = "amountTransfer", target = "item")
    public abstract EntityWithIdOutputDTO<AmountTransferDTO> toDtoWithId(AmountTransfer amountTransfer);

    @Override
    @Mapping(source = "from.bankAccountId", target = "sourceBankAccountId")
    @Mapping(source = "to.bankAccountId", target = "targetBankAccountId")
    @Mapping(source = "from.amount", target = "amount")
    @Mapping(source = "from.remark", target = "remark")
    public abstract AmountTransferDTO toDto(AmountTransfer amountTransfer);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "from.bankAccountId", source = "sourceBankAccountId")
    @Mapping(target = "to.bankAccountId", source = "targetBankAccountId")
    @Mapping(target = "from.amount", source = "amount")
    @Mapping(target = "from.remark", source = "remark")
    @Mapping(target = "to.amount", source = "amount")
    @Mapping(target = "to.remark", source = "remark")
    public abstract AmountTransfer toEntity(AmountTransferDTO amountTransferDTO);

    @Override
    public void merge(AmountTransferDTO eDTO, AmountTransfer entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected JpaRepository<AmountTransfer, Long> getRepository() {
        return repository;
    }
}
