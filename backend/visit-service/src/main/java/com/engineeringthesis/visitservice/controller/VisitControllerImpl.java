package com.engineeringthesis.visitservice.controller;

import com.engineeringthesis.commons.dto.user.PatientDTO;
import com.engineeringthesis.commons.dto.visit.*;
import com.engineeringthesis.commons.model.CrudController;
import com.engineeringthesis.commons.model.CrudResponse;
import com.engineeringthesis.visitservice.client.OpenAIClient;
import com.engineeringthesis.visitservice.client.UserServiceClient;
import com.engineeringthesis.visitservice.entity.MedicalExamination;
import com.engineeringthesis.visitservice.entity.Result;
import com.engineeringthesis.visitservice.entity.Visit;
import com.engineeringthesis.visitservice.mapper.ResultMapper;
import com.engineeringthesis.visitservice.mapper.VisitMapper;
import com.engineeringthesis.commons.model.visit.VisitStatus;
import com.engineeringthesis.visitservice.service.VisitServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/visit")
public class VisitControllerImpl implements CrudController<VisitDTO> {
    private final VisitServiceImpl visitService;
    private final VisitMapper visitMapper;
    private final ResultMapper resultMapper;

    @Autowired
    private OpenAIClient openAIClient;

    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> add(@Valid @RequestBody VisitDTO visitDTO) {
        Long visitId = visitDTO.getVisitId();
        log.info("Starting saving Visit with visitId: " + visitId);

        if (!userServiceClient.existsByPeselDoctor(visitDTO.getDoctorPesel()) ) {
            String message = String.format("Doctor with given PESEL doesn't exist in database!");
            return ResponseEntity.badRequest().body(new CrudResponse(Long.parseLong(visitDTO.getDoctorPesel()), message));
        }

        if (!userServiceClient.existsByPeselPatient(visitDTO.getPatientPesel())) {
            String message = String.format("Patient with given PESEL doesn't exist in database!");
            return ResponseEntity.badRequest().body(new CrudResponse(Long.parseLong(visitDTO.getPatientPesel()), message));
        }

        Visit visit = visitMapper.visitDTOToVisit(visitDTO);

        String patientPesel = visitDTO.getPatientPesel();
        PatientDTO patientDTO = userServiceClient.getByPesel(patientPesel).getBody();
        assert patientDTO != null;

        LocalDate womanBirthDate = patientDTO.getBirthDate();
        Period womanAge =  Period.between(womanBirthDate, LocalDate.now());

        visit.setWomanAge(womanAge.getYears());

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

    @RequestMapping(path = "/plannedVisits/{patientPesel}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VisitDTO>> getAllPlannedVisits(@PathVariable String patientPesel) {
        log.info("Starting getting list of all Visit objects");
        List<Visit> allVisits = visitService.getAllVisitsByPatientPesel(patientPesel);
        List<Visit> allPlannedVisits = new ArrayList<>();

        for (Visit visit : allVisits) {
            if (visit.getVisitStatus().equals(VisitStatus.SCHEDULED)) {
                allPlannedVisits.add(visit);
            }
        }

        List<VisitDTO> allPlannedVisitDTOS = allPlannedVisits.stream().map((visitMapper::visitToVisitDTO)).collect(Collectors.toList());
        return ResponseEntity.ok(allPlannedVisitDTOS);
    }

    @RequestMapping(path = "/myvisits/{patientPesel}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VisitDTO>> getAllMyVisits(@PathVariable String patientPesel) {
        log.info("Starting getting list of all my Visit objects");
        List<Visit> allVisits = visitService.getAllVisitsByPatientPesel(patientPesel);

        List<VisitDTO> allPlannedVisitDTOS = allVisits.stream().map((visitMapper::visitToVisitDTO)).collect(Collectors.toList());
        return ResponseEntity.ok(allPlannedVisitDTOS);
    }

    @RequestMapping(path = "/generateReportVisitId/{visitId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VisitResultsReportDTO> generateReportById(@PathVariable Long visitId) {
        log.info("Starting generating report for Visit with visitId: " + visitId);
        Visit visit = visitService.getById(visitId);
        VisitResultsReportDTO visitResultsReportDTO = new VisitResultsReportDTO();
        visitResultsReportDTO.setWeekOfPregnancy(visit.getWeekOfPregnancy());
        visitResultsReportDTO.setWomanAge(visit.getWomanAge());
        visitResultsReportDTO.setDoctorRecommendations(visit.getDoctorRecommendations());
        visitResultsReportDTO.setVisitDate(visit.getVisitDate());

        for (MedicalExamination medicalExamination : visit.getMedicalExaminations()) {
            String medicalExaminationName = medicalExamination.getMedicalExaminationName();
            List<Result> medicalExaminationResultsGroup = medicalExamination.getExactResults();
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

            visitResultsReportDTO.setWomanAge(visit.getWomanAge());
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

    @RequestMapping(path = "/analyzeReportsByPesel/{pesel}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String analyzeReportByOpenAI(@PathVariable String pesel) {
        log.info("Starting analyzing report with OpenAI for PESEL: " + pesel);
        List<VisitResultsReportDTO> visitResultsReportDTOS = generateReportByPatientPesel(pesel).getBody();
        StringBuilder resultStringBuilder = new StringBuilder();

        for (VisitResultsReportDTO reportDTO : visitResultsReportDTOS) {
            resultStringBuilder.append(reportDTO.toString()).append("\n");
        }


        String visitResultReportString = resultStringBuilder.toString();
        log.info("visitResultReportString: " + visitResultReportString);
        String response = openAIClient.analyzeReport(visitResultReportString);
        log.info("Response: " + response);

        return response;
    }

    @RequestMapping(path = "/generateDietPlan/{pesel}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DietPlanDayResponseDTO>> generateDietPlanByOpenAI(@PathVariable String pesel, @RequestBody DietPlanRequestDTO dietPlanRequestDTO) {
        log.info("Starting generating diet plan with OpenAI for PESEL: " + pesel);
        List<VisitResultsReportDTO> visitResultsReportDTOS = generateReportByPatientPesel(pesel).getBody();
        StringBuilder resultStringBuilder = new StringBuilder(". Tak wygladaja jej wyniki badan z poszczegolnych wizyt: ");

        for (VisitResultsReportDTO reportDTO : visitResultsReportDTOS) {
            resultStringBuilder.append(reportDTO.toString()).append("\n");
        }

        String visitResultReportString = resultStringBuilder.toString();

        StringBuilder frontendStringBuilder = new StringBuilder();
        frontendStringBuilder.append("\nLiczba dni dla ktorych trzeba wygenerowac plan dietetyczny: " + dietPlanRequestDTO.getDaysNumber().toString());
        frontendStringBuilder.append("\nLiczba posilkow do wygenerowania dla danego dnia tygodnia: " + dietPlanRequestDTO.getDishesNumber().toString());
        frontendStringBuilder.append("\nSumarycznie dieta na dany dzien musi sie zawierac liczbe kcal z przedzialu: " + dietPlanRequestDTO.getCaloriesRange() + " kcal");
        frontendStringBuilder.append("\nAlergeny: " + dietPlanRequestDTO.getAllergens());

        frontendStringBuilder.append("\nDiete podaj mi w odpowiednim formacie jako lista obiektow klasy " + DietPlanDayResponseDTO.class.getName() + " w postaci JSON: " + Arrays.toString(DietPlanDayResponseDTO.class.getDeclaredFields()));
        frontendStringBuilder.append("\nKlasa " + DishDTO.class.getName() + " wyglada tak: " + Arrays.toString(DishDTO.class.getDeclaredFields()));


        log.info("visitResultReportString: " + visitResultReportString);
        log.info("frontendStringBuilder" + frontendStringBuilder);

        StringBuilder combinedStringBuilder = new StringBuilder();
        combinedStringBuilder.append(frontendStringBuilder).append(visitResultReportString);
        String responseString = openAIClient.generateDietPlan(combinedStringBuilder.toString());
        responseString = responseString.replace("Odpowiedz:", "").trim();

        log.info("Response string: " + responseString);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<DietPlanDayResponseDTO>>() {}.getType();
        List<DietPlanDayResponseDTO> dietPlanDayResponseDTOList = gson.fromJson(responseString, listType);

        return new ResponseEntity<>(dietPlanDayResponseDTOList, HttpStatus.OK);
    }

    @RequestMapping(path = "/nearestPlannedVisit/{patientPesel}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VisitDTO> getById(@PathVariable String patientPesel) {
        log.info("Starting finding nearest Visit for patient with pesel: " + patientPesel);
        Optional<Visit> visit = visitService.findNearestPlannedVisit(patientPesel);

        VisitDTO visitDTO = new VisitDTO();
        if (visit.isPresent()) {
             visitDTO = visitMapper.visitToVisitDTO(visit.get());
        }
        return ResponseEntity.ok(visitDTO);
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
