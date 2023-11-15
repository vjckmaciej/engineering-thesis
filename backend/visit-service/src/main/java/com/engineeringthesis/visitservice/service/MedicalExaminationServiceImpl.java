package com.engineeringthesis.visitservice.service;

import com.engineeringthesis.commons.exception.DataSaveException;
import com.engineeringthesis.commons.exception.DeleteException;
import com.engineeringthesis.commons.model.CrudService;
import com.engineeringthesis.visitservice.entity.MedicalExamination;
import com.engineeringthesis.visitservice.repository.MedicalExaminationRepository;
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
public class MedicalExaminationServiceImpl implements CrudService<MedicalExamination> {
    private final MedicalExaminationRepository medicalExaminationRepository;


    @Override
    public void save(MedicalExamination medicalExamination) {
        Long medicalExaminationId = medicalExamination.getMedicalExaminationId();
        try {
            if (medicalExaminationRepository.existsByMedicalExaminationId(medicalExaminationId)) {
                String message = String.format("MedicalExamination with this medicalExaminationId: %d already exists in database!", medicalExaminationId);
                log.error(message);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
            }

            medicalExaminationRepository.save(medicalExamination);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save MedicalExamination with this medicalExaminationId: %d", medicalExaminationId);
            throw new DataSaveException(message);
        }
    }

    @Override
    public MedicalExamination getById(Long id) {
        String message = String.format("MedicalExamination with this medicalExaminationId: %d doesn't exist in database!", id);
        return medicalExaminationRepository.findByMedicalExaminationId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, message));
    }

    @Override
    public List<MedicalExamination> getAll() {
        return medicalExaminationRepository.findAll();
    }

    public List<MedicalExamination> getAllByVisitId(Long visitId) {
        String message = String.format("MedicalExaminations with this medicalExaminationId: %d don't exist in database!", visitId);
        return medicalExaminationRepository.findAllByVisitIdReference(visitId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, message));
    }

    @Override
    public void update(MedicalExamination medicalExamination) {
        Long medicalExaminationId = medicalExamination.getMedicalExaminationId();
        try {
            MedicalExamination oldMedicalExamination = medicalExaminationRepository.findByMedicalExaminationId(medicalExaminationId).orElseThrow(() -> {
                String message = String.format("%s with this medicalExaminationId: %d doesn't exist in database!", medicalExamination.getClass().getName(), medicalExaminationId);
//                return new EntityIdDoesNotExistException(message);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            });

            medicalExamination.setMedicalExaminationId(oldMedicalExamination.getMedicalExaminationId());

            medicalExaminationRepository.save(medicalExamination);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save MedicalExamination with this medicalExaminationId: %d", medicalExaminationId);
            throw new DataSaveException(message);
        }
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            if (!medicalExaminationRepository.existsByMedicalExaminationId(id)) {
                String message = String.format("Cannot delete MedicalExamination with medicalExaminationId: %d! Object doesn't exist in database!", id);
                //            throw new DeleteException(message);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            }
            medicalExaminationRepository.deleteByMedicalExaminationId(id);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot delete MedicalExamination with this medicalExaminationId: %d", id);
            throw new DeleteException(message);
        }
    }
}
