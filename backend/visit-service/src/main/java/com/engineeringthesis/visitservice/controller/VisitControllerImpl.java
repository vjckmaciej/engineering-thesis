package com.engineeringthesis.visitservice.controller;

import com.engineeringthesis.visitservice.dto.VisitDTO;
import com.engineeringthesis.visitservice.entity.Visit;
import com.engineeringthesis.visitservice.mapper.VisitMapper;
import com.engineeringthesis.visitservice.model.SaveResponse;
import com.engineeringthesis.visitservice.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class VisitControllerImpl implements VisitController {
    private final VisitService visitService;
    private final VisitMapper visitMapper;

    @Override
    @PostMapping("/add")
    public ResponseEntity<SaveResponse> addVisit(@RequestBody VisitDTO visitDTO) {
        Long visitId = visitDTO.getVisitId();
        log.info("Starting saving Visit with visitId = " + visitId);
        Visit visit = visitMapper.visitDTOToVisit(visitDTO);
        visitService.saveVisit(visit);
        return ResponseEntity.ok(new SaveResponse(visitId, "Visit successfully added to database!"));
    }
}
