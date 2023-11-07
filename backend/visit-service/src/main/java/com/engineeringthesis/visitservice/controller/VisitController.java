package com.engineeringthesis.visitservice.controller;

import com.engineeringthesis.visitservice.dto.VisitDTO;
import com.engineeringthesis.visitservice.model.SaveResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(path = "/visit")
public interface VisitController {
    @PostMapping
    ResponseEntity<SaveResponse> addVisit(VisitDTO visitDTO);
}
