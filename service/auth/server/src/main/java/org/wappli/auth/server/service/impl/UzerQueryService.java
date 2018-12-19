package org.wappli.auth.server.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wappli.auth.api.criteria.UzerCriteria;
import org.wappli.auth.server.domain.Uzer;
import org.wappli.auth.server.domain.Uzer_;
import org.wappli.auth.server.repository.UzerRepository;
import org.wappli.common.server.service.query.QueryService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UzerQueryService extends QueryService<Uzer, UzerCriteria> {

    private final org.wappli.auth.server.repository.UzerRepository uzerRepository;

    public UzerQueryService(UzerRepository UzerRepository) {
        this.uzerRepository = UzerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Uzer> findByCriteria(UzerCriteria uzerCriteria) {
        return uzerRepository.findAll(createSpecification(uzerCriteria));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Uzer> findByCriteria(UzerCriteria criteria, Pageable pageable) {
        return uzerRepository.findAll(createSpecification(criteria), pageable);
    }

    private Specification<Uzer> createSpecification(final UzerCriteria criteria) {
        Specification<Uzer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification
                        .and(buildSpecification(criteria.getId(), Uzer_.id));
            }


            if (criteria.getStatus() != null) {
                specification = specification
                        .and(buildSpecification(criteria.getStatus(), Uzer_.userStatus));
            }
        }

        return specification;
    }
}
