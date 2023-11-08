package com.engineeringthesis.commons.model;

import java.util.List;

public interface CrudService<TEntity> {
    void save(TEntity tEntity);

    void update(TEntity tEntity);

    TEntity getById(Long id);

    List<TEntity> getAll();

    void deleteById(Long id);
}
