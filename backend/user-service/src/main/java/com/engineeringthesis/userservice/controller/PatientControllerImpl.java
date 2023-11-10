package com.engineeringthesis.userservice.controller;

import com.engineeringthesis.commons.model.CrudController;
import com.engineeringthesis.commons.model.CrudPESELResponse;
import com.engineeringthesis.commons.model.CrudResponse;
import com.engineeringthesis.userservice.dto.PatientDTO;
import com.engineeringthesis.userservice.entity.Patient;
import com.engineeringthesis.userservice.mapper.PatientMapper;
import com.engineeringthesis.userservice.service.PatientServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/patient")
public class PatientControllerImpl implements CrudController<PatientDTO> {
    private final PatientServiceImpl patientService;
    private final PatientMapper patientMapper;

    @Override
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> add(@RequestBody PatientDTO patientDTO) {
        Long patientId = patientDTO.getPatientId();
        log.info("Starting saving Patient with patientId: " + patientId);
        Patient patient = patientMapper.patientDTOToPatient(patientDTO);
        patient.setRegistryDate(LocalDate.now());
        patientService.save(patient);
        return ResponseEntity.ok(new CrudResponse(patient.getPatientId(), "Patient added to database!"));
    }

    @Override
    @RequestMapping(path = "/id/{patientId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientDTO> getById(@PathVariable Long patientId) {
        log.info("Starting finding Patient with patientId: " + patientId);
        Patient patient = patientService.getById(patientId);
        PatientDTO patientDTO = patientMapper.patientToPatientDTO(patient);
        return ResponseEntity.ok(patientDTO);
    }

    @RequestMapping(path = "/pesel/{pesel}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientDTO> getByPesel(@PathVariable String pesel) {
        log.info("Starting finding Patient with PESEL: " + pesel);
        Patient patient = patientService.getByPesel(pesel);
        PatientDTO patientDTO = patientMapper.patientToPatientDTO(patient);
        return ResponseEntity.ok(patientDTO);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatientDTO>> getAll() {
        log.info("Starting getting list of all Patient objects");
        List<Patient> allPatients = patientService.getAll();
        List<PatientDTO> allPatientDTOS = allPatients.stream().map((patientMapper::patientToPatientDTO)).collect(Collectors.toList());
        return ResponseEntity.ok(allPatientDTOS);
    }

    @Override
    @RequestMapping(method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> update(@RequestBody PatientDTO patientDTO) {
        Long patientId = patientDTO.getPatientId();
        log.info(String.format("Starting Patient update with patientId: %d", patientId));
        Patient patient = patientMapper.patientDTOToPatient(patientDTO);
        patientService.update(patient);
        return ResponseEntity.ok(new CrudResponse(patientId, "Patient updated!"));
    }

    @RequestMapping(path = "/pesel", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudPESELResponse> updateByPesel(@RequestBody PatientDTO patientDTO) {
        String PESEL = patientDTO.getPesel();
        log.info(String.format("Starting Patient update with PESEL: %s", PESEL));
        Patient patient = patientMapper.patientDTOToPatient(patientDTO);
        patientService.updateByPesel(patient);
        return ResponseEntity.ok(new CrudPESELResponse(PESEL, "Patient updated!"));
    }

    @Override
    @RequestMapping(path = "/{patientId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> deleteById(@PathVariable Long patientId) {
        log.info(String.format("Starting deleting Patient by patientId: %d", patientId));
        patientService.deleteById(patientId);
        String message = String.format("Patient with patientId: %d deleted!", patientId);
        return ResponseEntity.ok(new CrudResponse(patientId, message));
    }

    @RequestMapping(path = "/pesel/{pesel}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudPESELResponse> deleteByPesel(@PathVariable String pesel) {
        log.info(String.format("Starting deleting Patient by PESEL: %s", pesel));
        patientService.deleteByPesel(pesel);
        String message = String.format("Patient with PESEL: %s deleted!", pesel);
        return ResponseEntity.ok(new CrudPESELResponse(pesel, message));
    }
}
