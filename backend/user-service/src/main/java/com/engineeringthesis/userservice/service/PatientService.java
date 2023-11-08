package com.engineeringthesis.userservice.service;

import com.engineeringthesis.commons.exception.DataSaveException;
import com.engineeringthesis.commons.exception.DeleteException;
import com.engineeringthesis.commons.exception.EntityIdAlreadyExistException;
import com.engineeringthesis.commons.exception.EntityIdDoesNotExistException;
import com.engineeringthesis.commons.exception.ObjectDoesNotExistException;
import com.engineeringthesis.commons.model.CrudService;
import com.engineeringthesis.userservice.entity.Patient;
import com.engineeringthesis.userservice.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService implements CrudService<Patient> {
    private final PatientRepository patientRepository;

    @Override
    public void save(Patient patient) {
        Long patientId = patient.getPatientId();
        try {
            if (patientRepository.existsByPatientId(patientId)) {
                String message = String.format("Patient with this patientId: %d already exists in database!", patientId);
//                throw new EntityIdAlreadyExistException(message);
                log.error(message);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
            }
            patientRepository.save(patient);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save Patient with this patientId: %d", patientId);
            throw new DataSaveException(message);
        }
    }

    @Override
    public void update(Patient patient) {
        Long patientId = patient.getPatientId();
        try {
            Patient oldPatient = patientRepository.findByPatientId(patientId).orElseThrow(() -> {
                String message = String.format("%s with this patientId: %d doesn't exists in database!", patient.getClass().getName(), patientId);
                return new EntityIdDoesNotExistException(message);
            });

            patient.setPatientId(oldPatient.getPatientId());

            patientRepository.save(patient);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save Visit with this visitId: %d", patientId);
            throw new DataSaveException(message);
        }
    }

    @Override
    public Patient getById(Long id) {
        String message = String.format("Patient with this patientId: %d doesn't exists in database!", id);
        return patientRepository.findByPatientId(id).orElseThrow(() -> new ObjectDoesNotExistException(message));
    }

    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!patientRepository.existsByPatientId(id)) {
            String message = String.format("Cannot delete Patient with patientId: %d! Object doesn't exists in database!", id);
            throw new DeleteException(message);
        }
        patientRepository.deleteByPatientId(id);
    }
}
