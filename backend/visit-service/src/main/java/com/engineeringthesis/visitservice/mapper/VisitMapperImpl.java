package com.engineeringthesis.visitservice.mapper;

import com.engineeringthesis.visitservice.dto.VisitDTO;
import com.engineeringthesis.visitservice.entity.Visit;
import org.springframework.stereotype.Component;

@Component
public class VisitMapperImpl implements VisitMapper {
    @Override
    public Visit visitDTOToVisit(VisitDTO visitDTO) {
        if (visitDTO == null) {
            return null;
        }

        Visit visit = new Visit();

        visit.setVisitId(visitDTO.getVisitId());
        visit.setVisitDate(visitDTO.getVisitDate());
        visit.setDoctorRecommendations(visitDTO.getDoctorRecommendations());

        return visit;
    }

    @Override
    public VisitDTO visitToVisitDTO(Visit visit) {
        if (visit == null) {
            return null;
        }

        VisitDTO visitDTO = new VisitDTO();

        visitDTO.setVisitId(visit.getVisitId());
        visitDTO.setVisitDate(visit.getVisitDate());
        visitDTO.setDoctorRecommendations(visit.getDoctorRecommendations());

        return visitDTO;
    }
}
