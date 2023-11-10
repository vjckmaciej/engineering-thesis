package com.engineeringthesis.visitservice.controller;

import com.engineeringthesis.commons.model.CrudResponse;
import com.engineeringthesis.visitservice.dto.VisitDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(path = "/visit")
public interface VisitController {
    @PostMapping
    ResponseEntity<CrudResponse> addVisit(VisitDTO visitDTO);
}
