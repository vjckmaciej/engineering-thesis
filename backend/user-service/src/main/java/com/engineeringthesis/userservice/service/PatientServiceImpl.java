package com.engineeringthesis.userservice.service;

import com.engineeringthesis.commons.exception.DataSaveException;
import com.engineeringthesis.commons.exception.DeleteException;
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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements CrudService<Patient> {
    private final PatientRepository patientRepository;

    @Override
    public void save(Patient patient) {
        String PESEL = patient.getPesel();
        try {
            if (patientRepository.existsByPesel(PESEL)) {
                String message = String.format("Patient with this PESEL: %s already exists in database!", PESEL);
//                throw new EntityIdAlreadyExistException(message);
                log.error(message);
                //TODO: check handling exceptions
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
            }

            patientRepository.save(patient);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save Patient with this PESEL: %d", PESEL);
            throw new DataSaveException(message);
        }
    }

    @Override
    public Patient getById(Long id) {
        String message = String.format("Patient with this patientId: %d doesn't exist in database!", id);
//        return patientRepository.findByPatientId(id).orElseThrow(() -> new ObjectDoesNotExistException(message));
        return patientRepository.findByPatientId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, message));
    }

    public Patient getByPesel(String pesel) {
        String message = String.format("Patient with this PESEL: %s doesn't exist in database!", pesel);
//        return patientRepository.findByPatientId(id).orElseThrow(() -> new ObjectDoesNotExistException(message));
        return patientRepository.findByPesel(pesel).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, message));
    }

    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Integer getPregnancyWeekByPesel(String pesel) {
        String message = String.format("Patient with this PESEL: %s doesn't exist in database!", pesel);
        Patient optionalPatient = patientRepository.findByPesel(pesel).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, message));
        return Math.toIntExact(ChronoUnit.WEEKS.between(optionalPatient.getPregnancyStartDate(), LocalDate.now()));
    }

    @Override
    public void update(Patient patient) {
        Long patientId = patient.getPatientId();
        try {
            Patient oldPatient = patientRepository.findByPatientId(patientId).orElseThrow(() -> {
                String message = String.format("%s with this patientId: %d doesn't exist in database!", patient.getClass().getName(), patientId);
//                return new EntityIdDoesNotExistException(message);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            });

            patient.setPatientId(oldPatient.getPatientId());

            patientRepository.save(patient);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save Patient with this patientId: %d", patientId);
            throw new DataSaveException(message);
        }
    }

    public void updateByPesel(Patient patient) {
        String pesel = patient.getPesel();
        try {
            Patient oldPatient = patientRepository.findByPesel(pesel).orElseThrow(() -> {
                String message = String.format("%s with this PESEL: %s doesn't exist in database!", patient.getClass().getName(), pesel);
//                return new EntityIdDoesNotExistException(message);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            });

            patient.setPatientId(oldPatient.getPatientId());

            patientRepository.save(patient);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save Patient with this PESEL: %s", pesel);
            throw new DataSaveException(message);
        }
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            if (!patientRepository.existsByPatientId(id)) {
                String message = String.format("Cannot delete Patient with patientId: %d! Object doesn't exist in database!", id);
                log.error(message);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            }
            patientRepository.deleteByPatientId(id);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot delete Patient with this patientId: %d", id);
            throw new DeleteException(message);
        }
    }

    @Transactional
    public void deleteByPesel(String PESEL) {
        try {
            if (!patientRepository.existsByPesel(PESEL)) {
                String message = String.format("Cannot delete Patient with PESEL: %s! Object doesn't exist in database!", PESEL);
                log.error(message);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            }
            patientRepository.deleteByPesel(PESEL);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot delete Patient with this PESEL: %s", PESEL);
            throw new DeleteException(message);
        }
    }
}
