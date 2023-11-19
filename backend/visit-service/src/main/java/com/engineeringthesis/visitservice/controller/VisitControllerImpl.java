package com.engineeringthesis.visitservice.controller;

import com.engineeringthesis.commons.model.CrudController;
import com.engineeringthesis.commons.model.CrudResponse;
import com.engineeringthesis.visitservice.dto.ResultsReportDTO;
import com.engineeringthesis.visitservice.dto.VisitDTO;
import com.engineeringthesis.visitservice.dto.VisitResultsReportDTO;
import com.engineeringthesis.visitservice.entity.MedicalExamination;
import com.engineeringthesis.visitservice.entity.Result;
import com.engineeringthesis.visitservice.entity.Visit;
import com.engineeringthesis.visitservice.mapper.ResultMapper;
import com.engineeringthesis.visitservice.mapper.VisitMapper;
import com.engineeringthesis.visitservice.model.VisitStatus;
import com.engineeringthesis.visitservice.service.VisitServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/visit")
public class VisitControllerImpl implements CrudController<VisitDTO> {
    private final VisitServiceImpl visitService;
    private final VisitMapper visitMapper;
    private final ResultMapper resultMapper;

    @Override
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> add(@RequestBody VisitDTO visitDTO) {
        Long visitId = visitDTO.getVisitId();
        log.info("Starting saving Visit with visitId: " + visitId);
        Visit visit = visitMapper.visitDTOToVisit(visitDTO);
        visit.setVisitStatus(VisitStatus.SCHEDULED);
        visit.setDoctorRecommendations("There are no any doctor recommendations yet!");
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

    @RequestMapping(path = "/produceReportVisitId/{visitId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VisitResultsReportDTO> generateReportById(@PathVariable Long visitId) {
        log.info("Starting generating report for Visit with visitId: " + visitId);
        Visit visit = visitService.getById(visitId);
        VisitResultsReportDTO visitResultsReportDTO = new VisitResultsReportDTO();
        visitResultsReportDTO.setWeekOfPregnancy(visit.getWeekOfPregnancy());
        visitResultsReportDTO.setDoctorRecommendations(visit.getDoctorRecommendations());
        visitResultsReportDTO.setVisitDate(visit.getVisitDate());

        for (MedicalExamination medicalExamination : visit.getMedicalExaminations()) {
            String medicalExaminationName = medicalExamination.getMedicalExaminationName();
            List<Result> medicalExaminationResultsGroup = medicalExamination.getExactResults();
//            List<ResultDTO> medicalExaminationResultsGroupDTO = medicalExaminationResultsGroup.stream().map((resultMapper::resultToResultDTO)).toList();
            List<ResultsReportDTO> medicalExaminationResultsGroupDTO = medicalExaminationResultsGroup.stream().map((resultMapper::resultToResultsReportDTO)).toList();

            visitResultsReportDTO.getResults().put(medicalExaminationName, medicalExaminationResultsGroupDTO);
        }

        return ResponseEntity.ok(visitResultsReportDTO);
    }

    @RequestMapping(path = "/generateReportPatientPesel/{patientPesel}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VisitResultsReportDTO>> generateReportByPatientPesel(@PathVariable String patientPesel) {
        log.info("Starting generating report for Visits with patient pesel: " + patientPesel);
        List<Visit> allVisitsWithGivenPatientPesel = visitService.getAllVisitsByPatientPesel(patientPesel);
        List<VisitResultsReportDTO> allVisitResultsReportDTO = new ArrayList<>();

        for (Visit visit : allVisitsWithGivenPatientPesel) {
            VisitResultsReportDTO visitResultsReportDTO = new VisitResultsReportDTO();
            visitResultsReportDTO.setWeekOfPregnancy(visit.getWeekOfPregnancy());
            visitResultsReportDTO.setDoctorRecommendations(visit.getDoctorRecommendations());
            visitResultsReportDTO.setVisitDate(visit.getVisitDate());

            for (MedicalExamination medicalExamination : visit.getMedicalExaminations()) {
                String medicalExaminationName = medicalExamination.getMedicalExaminationName();
                List<Result> medicalExaminationResultsGroup = medicalExamination.getExactResults();
//            List<ResultDTO> medicalExaminationResultsGroupDTO = medicalExaminationResultsGroup.stream().map((resultMapper::resultToResultDTO)).toList();
                List<ResultsReportDTO> medicalExaminationResultsGroupDTO = medicalExaminationResultsGroup.stream().map((resultMapper::resultToResultsReportDTO)).toList();

                visitResultsReportDTO.getResults().put(medicalExaminationName, medicalExaminationResultsGroupDTO);
            }

            allVisitResultsReportDTO.add(visitResultsReportDTO);
        }

        return ResponseEntity.ok(allVisitResultsReportDTO);
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
