package com.engineeringthesis.visitservice.service;

import com.engineeringthesis.commons.exception.DataSaveException;
import com.engineeringthesis.commons.exception.DeleteException;
import com.engineeringthesis.commons.model.CrudService;
import com.engineeringthesis.visitservice.entity.Visit;
import com.engineeringthesis.visitservice.repository.VisitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitServiceImpl implements CrudService<Visit> {
    private final VisitRepository visitRepository;

    @Override
    public void save(Visit visit) {
        Long visitId = visit.getVisitId();
        try {
            if (visitRepository.existsByVisitId(visitId)) {
                String message = String.format("Visit with this visitId: %d already exists in database!", visitId);
                log.error(message);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
            }

            visitRepository.save(visit);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save Visit with this visitId: %d", visitId);
            throw new DataSaveException(message);
        }
    }

    @Override
    public Visit getById(Long id) {
        String message = String.format("Visit with this visitId: %d doesn't exist in database!", id);
        return visitRepository.findByVisitId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, message));
    }

    @Override
    public List<Visit> getAll() {
        return visitRepository.findAll();
    }

    public List<Visit> getAllVisitsByPatientPesel(String patientPesel) { return  visitRepository.findAllByPatientPesel(patientPesel); }

    public Optional<Visit> findNearestPlannedVisit(String patientPesel) {
        return visitRepository.findNextScheduledVisitByPatientPesel(patientPesel);
    }

    @Override
    public void update(Visit visit) {
        Long visitId = visit.getVisitId();
        try {
            Visit oldVisit = visitRepository.findByVisitId(visitId).orElseThrow(() -> {
                String message = String.format("%s with this visitId: %d doesn't exist in database!", visit.getClass().getName(), visitId);
//                return new EntityIdDoesNotExistException(message);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            });

            visit.setVisitId(oldVisit.getVisitId());

            if (visit.getDoctorPesel() != null && !Objects.equals(visit.getDoctorPesel(), oldVisit.getDoctorPesel())) {
                oldVisit.setDoctorPesel(visit.getDoctorPesel());
            }
            if (visit.getPatientPesel() != null && !Objects.equals(visit.getPatientPesel(), oldVisit.getPatientPesel())) {
                oldVisit.setPatientPesel(visit.getPatientPesel());
            }

            if (visit.getVisitDate() != null && !Objects.equals(visit.getVisitDate(), oldVisit.getVisitDate())) {
                oldVisit.setVisitDate(visit.getVisitDate());
            }
            if (visit.getVisitStatus() != null && !Objects.equals(visit.getVisitStatus(), oldVisit.getVisitStatus())) {
                oldVisit.setVisitStatus(visit.getVisitStatus());
            }
            if (visit.getWeekOfPregnancy() != null && !Objects.equals(visit.getWeekOfPregnancy(), oldVisit.getWeekOfPregnancy())) {
                oldVisit.setWeekOfPregnancy(visit.getWeekOfPregnancy());
            }
            if (visit.getDoctorRecommendations() != null && !Objects.equals(visit.getDoctorRecommendations(), oldVisit.getDoctorRecommendations())) {
                oldVisit.setDoctorRecommendations(visit.getDoctorRecommendations());
            }

            visitRepository.save(oldVisit);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save Visit with this visitId: %d", visitId);
            throw new DataSaveException(message);
        }
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            if (!visitRepository.existsByVisitId(id)) {
                String message = String.format("Cannot delete Visit with visitId: %d! Object doesn't exist in database!", id);
                //            throw new DeleteException(message);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            }
            visitRepository.deleteByVisitId(id);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot delete Visit with this visitId: %d", id);
            throw new DeleteException(message);
        }
    }
}
