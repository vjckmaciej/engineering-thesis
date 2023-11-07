package com.engineeringthesis.visitservice.mapper;

import com.engineeringthesis.visitservice.controller.VisitController;
import com.engineeringthesis.visitservice.dto.VisitDTO;
import com.engineeringthesis.visitservice.entity.Visit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = VisitController.class)
public interface VisitMapper {
    Visit visitDTOToVisit(VisitDTO visitDTO);

    VisitDTO visitToVisitDTO(Visit visit);
}
