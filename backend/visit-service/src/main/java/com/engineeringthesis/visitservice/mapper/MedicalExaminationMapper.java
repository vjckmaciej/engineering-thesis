package com.engineeringthesis.visitservice.mapper;

import com.engineeringthesis.visitservice.dto.MedicalExaminationDTO;
import com.engineeringthesis.visitservice.entity.MedicalExamination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MedicalExaminationMapper {
    @Mapping(target = "medicalExaminationId", ignore = true)
    @Mapping(target = "visitId", ignore = true)
    @Mapping(target = "exactResults", source = "medicalExaminationDTO.results")
    MedicalExamination medicalExaminationDTOToMedicalExamination(MedicalExaminationDTO medicalExaminationDTO);

    @Mapping(target = "medicalExaminationId", source = "medicalExamination.medicalExaminationId")
    @Mapping(target = "results", source = "medicalExamination.exactResults")
    MedicalExaminationDTO medicalExaminationToMedicalExaminationDTO(MedicalExamination medicalExamination);
}
