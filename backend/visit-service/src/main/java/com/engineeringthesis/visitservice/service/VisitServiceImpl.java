package com.engineeringthesis.visitservice.service;

import com.engineeringthesis.visitservice.entity.Visit;
import com.engineeringthesis.visitservice.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitServiceImpl implements VisitService {
    private final VisitRepository visitRepository;

    @Override
    public void saveVisit(Visit visit) {
        Long visitId = visit.getVisitId();
        try {
            if (visitRepository.existsByVisitId(visitId)) {
                String message = String.format("Visit with this visitId: %d already exists in database!", visitId);
            }
            visitRepository.save(visit);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save Visit with this visitId: %d", visitId);

        }
    }
}
