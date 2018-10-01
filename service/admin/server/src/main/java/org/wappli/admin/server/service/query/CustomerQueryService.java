package org.wappli.admin.server.service.query;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wappli.admin.api.rest.criteria.CustomerCriteria;
import org.wappli.admin.server.domain.Customer;
import org.wappli.admin.server.domain.Customer_;
import org.wappli.admin.server.repository.CustomerRepository;
import org.wappli.common.server.service.query.AbstractQueryService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomerQueryService extends AbstractQueryService<Customer, CustomerCriteria> {
    private static final Logger log = LoggerFactory.getLogger(CustomerQueryService.class);

    private final CustomerRepository customerRepository;

    public CustomerQueryService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public List<Customer> findByCriteria(CustomerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);

        final Specification<Customer> specification = createSpecification(criteria);
        return customerRepository.findAll(specification);
    }

    @Transactional(readOnly = true)
    public Page<Customer> findByCriteria(CustomerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);

        final Specification<Customer> specification = createSpecification(criteria);
        return customerRepository.findAll(specification, page);
    }

    private Specification<Customer> createSpecification(CustomerCriteria criteria) {
        Specification<Customer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Customer_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildSpecification(criteria.getName(), Customer_.name));
            }
        }

        return specification;
    }

}
