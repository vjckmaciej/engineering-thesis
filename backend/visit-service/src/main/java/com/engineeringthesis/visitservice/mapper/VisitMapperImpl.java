package com.engineeringthesis.visitservice.mapper;

import com.engineeringthesis.visitservice.dto.VisitDTO;
import com.engineeringthesis.visitservice.entity.Visit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class VisitMapperImpl implements VisitMapper {

    @Override
    public Visit visitDTOToVisit(VisitDTO visitDTO) {
        if (visitDTO == null) {
            return null;
        }

        Visit visit = new Visit();

        visit.setVisitId(visitDTO.getVisitId());
        visit.setVisitDate(visitDTO.getVisitDate());
        visit.setVisitStatus(visitDTO.getVisitStatus());
//        visit.setDoctorId(visitDTO.getDoctorId());
//        visit.setPatientId(visitDTO.getPatientId());
        visit.setDoctorPesel(visitDTO.getDoctorPesel());
        visit.setPatientPesel(visitDTO.getPatientPesel());
        visit.setWeekOfPregnancy(visitDTO.getWeekOfPregnancy());
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
//        visitDTO.setDoctorId(visit.getDoctorId());
//        visitDTO.setPatientId(visit.getPatientId());
        visitDTO.setDoctorPesel(visit.getDoctorPesel());
        visitDTO.setPatientPesel(visit.getPatientPesel());
        visitDTO.setVisitStatus(visit.getVisitStatus());
        visitDTO.setWeekOfPregnancy(visit.getWeekOfPregnancy());
        visitDTO.setDoctorRecommendations(visit.getDoctorRecommendations());

        return visitDTO;
    }
}
