package com.engineeringthesis.visitservice.controller;

import com.engineeringthesis.commons.model.CrudController;
import com.engineeringthesis.commons.model.CrudResponse;
import com.engineeringthesis.visitservice.dto.MedicalExaminationDTO;
import com.engineeringthesis.visitservice.entity.MedicalExamination;
import com.engineeringthesis.visitservice.mapper.MedicalExaminationMapper;
import com.engineeringthesis.visitservice.service.MedicalExaminationServiceImpl;
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
@RequestMapping(path = "/medicalExamination")
public class MedicalExaminationControllerImpl implements CrudController<MedicalExaminationDTO> {
    private final MedicalExaminationServiceImpl medicalExaminationService;
    private final MedicalExaminationMapper medicalExaminationMapper;

    @Override
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> add(@RequestBody MedicalExaminationDTO medicalExaminationDTO) {
        Long medicalExaminationId = medicalExaminationDTO.getMedicalExaminationId();
        log.info("Starting saving MedicalExamination with medicalExaminationId: " + medicalExaminationId);
        MedicalExamination medicalExamination = medicalExaminationMapper.medicalExaminationDTOToMedicalExamination(medicalExaminationDTO);
        medicalExaminationService.save(medicalExamination);
        return ResponseEntity.ok(new CrudResponse(medicalExamination.getMedicalExaminationId(), "MedicalExamination added to database!"));
    }

    @Override
    @RequestMapping(path = "/{medicalExaminationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicalExaminationDTO> getById(@PathVariable Long medicalExaminationId) {
        log.info("Starting finding MedicalExamination with medicalExaminationId: " + medicalExaminationId);
        MedicalExamination medicalExamination = medicalExaminationService.getById(medicalExaminationId);
        MedicalExaminationDTO medicalExaminationDTO = medicalExaminationMapper.medicalExaminationToMedicalExaminationDTO(medicalExamination);
        return ResponseEntity.ok(medicalExaminationDTO);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicalExaminationDTO>> getAll() {
        log.info("Starting getting list of all MedicalExamination objects");
        List<MedicalExamination> allMedicalExaminations = medicalExaminationService.getAll();
        List<MedicalExaminationDTO> allMedicalExaminationDTOS = allMedicalExaminations.stream().map((medicalExaminationMapper::medicalExaminationToMedicalExaminationDTO)).collect(Collectors.toList());
        return ResponseEntity.ok(allMedicalExaminationDTOS);
    }

    @RequestMapping(path = "/filter/{visitId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicalExaminationDTO>> getAllByVisitId(@PathVariable Long visitId) {
        log.info("Starting getting list of all MedicalExamination objects with given visitId: " + visitId);
        List<MedicalExamination> allMedicalExaminations = medicalExaminationService.getAllByVisitId(visitId);
        List<MedicalExaminationDTO> allMedicalExaminationDTOS = allMedicalExaminations.stream().map((medicalExaminationMapper::medicalExaminationToMedicalExaminationDTO)).collect(Collectors.toList());
        return ResponseEntity.ok(allMedicalExaminationDTOS);
    }

    @Override
    @RequestMapping(method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> update(@RequestBody MedicalExaminationDTO medicalExaminationDTO) {
        Long medicalExaminationId = medicalExaminationDTO.getMedicalExaminationId();
        log.info(String.format("Starting MedicalExamination update with medicalExaminationId: %d", medicalExaminationId));
        MedicalExamination medicalExamination = medicalExaminationMapper.medicalExaminationDTOToMedicalExamination(medicalExaminationDTO);
        medicalExaminationService.update(medicalExamination);
        return ResponseEntity.ok(new CrudResponse(medicalExaminationId, "MedicalExamination updated!"));
    }

    @Override
    @RequestMapping(path = "/{medicalExaminationId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> deleteById(@PathVariable Long medicalExaminationId) {
        log.info(String.format("Starting deleting MedicalExamination by medicalExaminationId: %d", medicalExaminationId));
        medicalExaminationService.deleteById(medicalExaminationId);
        String message = String.format("MedicalExamination with medicalExaminationId: %d deleted!", medicalExaminationId);
        return ResponseEntity.ok(new CrudResponse(medicalExaminationId, message));
    }
}
