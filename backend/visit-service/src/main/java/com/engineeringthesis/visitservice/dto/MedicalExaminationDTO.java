package com.engineeringthesis.visitservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalExaminationDTO {
    private Long medicalExaminationId;
    private String medicalExaminationName;
    private Long visitId;
}
