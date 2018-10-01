package org.wappli.admin.server.service.query;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wappli.admin.api.rest.criteria.BankAccountCriteria;
import org.wappli.admin.server.domain.BankAccount;
import org.wappli.admin.server.domain.BankAccount_;
import org.wappli.admin.server.repository.BankAccountRepository;
import org.wappli.common.server.service.query.AbstractQueryService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BankAccountQueryService extends AbstractQueryService<BankAccount, BankAccountCriteria> {
    private static final Logger log = LoggerFactory.getLogger(BankAccountQueryService.class);

    private final BankAccountRepository dokumentumRepository;

    public BankAccountQueryService(BankAccountRepository dokumentumRepository) {
        this.dokumentumRepository = dokumentumRepository;
    }

    @Transactional(readOnly = true)
    public List<BankAccount> findByCriteria(BankAccountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);

        final Specification<BankAccount> specification = createSpecification(criteria);
        return dokumentumRepository.findAll(specification);
    }

    @Transactional(readOnly = true)
    public Page<BankAccount> findByCriteria(BankAccountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);

        final Specification<BankAccount> specification = createSpecification(criteria);
        return dokumentumRepository.findAll(specification, page);
    }

    private Specification<BankAccount> createSpecification(BankAccountCriteria criteria) {
        Specification<BankAccount> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BankAccount_.id));
            }
            if (criteria.getCurrency() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrency(), BankAccount_.currency));
            }
        }

        return specification;
    }

}
