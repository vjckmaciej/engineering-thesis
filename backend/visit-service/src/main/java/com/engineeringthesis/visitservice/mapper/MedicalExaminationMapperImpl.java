package com.engineeringthesis.visitservice.mapper;

import com.engineeringthesis.visitservice.dto.MedicalExaminationDTO;
import com.engineeringthesis.visitservice.entity.MedicalExamination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MedicalExaminationMapperImpl implements MedicalExaminationMapper {
    @Override
    public MedicalExamination medicalExaminationDTOToMedicalExamination(MedicalExaminationDTO medicalExaminationDTO) {
        if (medicalExaminationDTO == null) {
            return null;
        }

        MedicalExamination medicalExamination = new MedicalExamination();

        medicalExamination.setMedicalExaminationId(medicalExaminationDTO.getMedicalExaminationId());
        medicalExamination.setMedicalExaminationName(medicalExaminationDTO.getMedicalExaminationName());

        return medicalExamination;
    }

    @Override
    public MedicalExaminationDTO medicalExaminationToMedicalExaminationDTO(MedicalExamination medicalExamination) {
        if (medicalExamination == null) {
            return null;
        }

        MedicalExaminationDTO medicalExaminationDTO = new MedicalExaminationDTO();

        medicalExaminationDTO.setMedicalExaminationId(medicalExamination.getMedicalExaminationId());
        medicalExaminationDTO.setMedicalExaminationName(medicalExamination.getMedicalExaminationName());

        return medicalExaminationDTO;
    }
}
