package com.engineeringthesis.visitservice.mapper;

import com.engineeringthesis.commons.dto.visit.VisitDTO;
import com.engineeringthesis.visitservice.entity.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface VisitMapper {
    @Mapping(target = "patient", source = "patientId")
    @Mapping(target = "doctor", source = "doctorId")
    Visit visitDTOToVisit(VisitDTO visitDTO);


    @Mapping(target = "patientId", source = "patientId")
    @Mapping(target = "doctorId", source = "doctorId")
    VisitDTO visitToVisitDTO(Visit visit);
}
