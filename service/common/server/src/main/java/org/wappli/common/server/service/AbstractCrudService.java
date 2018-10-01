package org.wappli.common.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wappli.common.server.domain.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public abstract class AbstractCrudService<ENTITY extends AbstractEntity, REPOSITORY extends JpaRepository<ENTITY, Long>>
        implements CrudService<ENTITY> {
    private static final Logger log = LoggerFactory.getLogger(AbstractCrudService.class);

    protected final REPOSITORY repository;

    public AbstractCrudService(REPOSITORY repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public ENTITY create(ENTITY entity) {
        log.debug("Request to save : {}", entity);

        return this.repository.save(entity);
    }

    @Override
    @Transactional
    public ENTITY update(ENTITY target) {
        log.debug("Request to update : {}", target);

        return this.repository.save(target);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ENTITY> findAll(Pageable pageable) {
        log.debug("Request to get all from repository {}", getRepoName());

        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public ENTITY findOne(Long id) {
        log.debug("Request to get one from repository : {}, with id {}", getRepoName(), id);

        return repository.findById(id).orElse(null);
    }

    private String getRepoName() {
        return repository.getClass().getSimpleName();
    }

    @Override
    @Transactional
    public void delete(ENTITY entity) {
        log.debug("Request to delete entity : {}", entity);

        this.repository.delete(entity);
    }
}
