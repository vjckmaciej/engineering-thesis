package com.engineeringthesis.visitservice.controller;

import com.engineeringthesis.commons.model.CrudController;
import com.engineeringthesis.commons.model.CrudResponse;
import com.engineeringthesis.visitservice.dto.VisitDTO;
import com.engineeringthesis.visitservice.entity.Visit;
import com.engineeringthesis.visitservice.mapper.VisitMapper;
import com.engineeringthesis.visitservice.model.VisitStatus;
import com.engineeringthesis.visitservice.service.VisitServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/visit")
public class VisitControllerImpl implements CrudController<VisitDTO> {
    private final VisitServiceImpl visitService;
    private final VisitMapper visitMapper;

    @Override
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> add(@RequestBody VisitDTO visitDTO) {
        Long visitId = visitDTO.getVisitId();
        log.info("Starting saving Visit with visitId: " + visitId);
        Visit visit = visitMapper.visitDTOToVisit(visitDTO);
        visit.setVisitStatus(VisitStatus.SCHEDULED);
        visitService.save(visit);
        return ResponseEntity.ok(new CrudResponse(visit.getVisitId(), "Visit added to database!"));
    }

    @Override
    @RequestMapping(path = "/{visitId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VisitDTO> getById(@PathVariable Long visitId) {
        log.info("Starting finding Visit with visitId: " + visitId);
        Visit visit = visitService.getById(visitId);
        VisitDTO visitDTO = visitMapper.visitToVisitDTO(visit);
        return ResponseEntity.ok(visitDTO);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VisitDTO>> getAll() {
        log.info("Starting getting list of all Visit objects");
        List<Visit> allVisits = visitService.getAll();
        List<VisitDTO> allVisitDTOS = allVisits.stream().map((visitMapper::visitToVisitDTO)).collect(Collectors.toList());
        return ResponseEntity.ok(allVisitDTOS);
    }

    @Override
    @RequestMapping(method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> update(@RequestBody VisitDTO visitDTO) {
        Long visitId = visitDTO.getVisitId();
        log.info(String.format("Starting Visit update with visitId: %d", visitId));
        Visit visit = visitMapper.visitDTOToVisit(visitDTO);
        visitService.update(visit);
        return ResponseEntity.ok(new CrudResponse(visitId, "Visit updated!"));
    }

    @Override
    @RequestMapping(path = "/{visitId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> deleteById(@PathVariable Long visitId) {
        log.info(String.format("Starting deleting Visit by visitId: %d", visitId));
        visitService.deleteById(visitId);
        String message = String.format("Visit with visitId: %d deleted!", visitId);
        return ResponseEntity.ok(new CrudResponse(visitId, message));
    }
}
