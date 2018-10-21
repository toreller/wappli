package org.wappli.admin.server.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.wappli.admin.api.rest.dto.entities.CustomerDTO;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.admin.server.domain.Customer;
import org.wappli.admin.server.repository.CustomerRepository;
import org.wappli.common.server.service.mapper.MapperUsingRepository;

@Mapper(componentModel = "spring")
public abstract class CustomerMapper extends MapperUsingRepository<CustomerDTO, Customer> {

    @Autowired
    private CustomerRepository repository;

    @Override
    @Mapping(source = "customer", target = "item")
    public abstract EntityWithIdOutputDTO<CustomerDTO> toDtoWithId(Customer customer);

    @Override
    public abstract CustomerDTO toDto(Customer customer);

    @Override
    @Mapping(target = "id", ignore = true)
    public abstract void merge(CustomerDTO customerDTO, @MappingTarget Customer entity);

    @Override
    @Mapping(target = "id", ignore = true)
    public abstract Customer toEntity(CustomerDTO customerDTO);

    @Override
    protected JpaRepository<Customer, Long> getRepository() {
        return repository;
    }
}
