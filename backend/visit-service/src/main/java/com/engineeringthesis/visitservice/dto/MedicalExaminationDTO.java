package com.engineeringthesis.visitservice.dto;

import com.engineeringthesis.visitservice.entity.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalExaminationDTO {
    private Long medicalExaminationId;
    private String medicalExaminationName;
    private List<Result> exactResults;
}
