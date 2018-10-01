package org.wappli.common.server.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<T> {

    T create(T entity);

    T update(T current);

    Page<T> findAll(Pageable pageable);

    T findOne(Long id);

    void delete(T entity);

}
