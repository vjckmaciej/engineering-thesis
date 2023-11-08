package com.engineeringthesis.commons.model;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CrudController<TEntityDTO> {
    ResponseEntity<CrudResponse> add(TEntityDTO tEntityDTO);

    ResponseEntity<TEntityDTO> getById(Long id);

    ResponseEntity<List<TEntityDTO>> getAll();

    ResponseEntity<CrudResponse> update(TEntityDTO tEntityDTO);

    ResponseEntity<CrudResponse> deleteById(Long id);
}
