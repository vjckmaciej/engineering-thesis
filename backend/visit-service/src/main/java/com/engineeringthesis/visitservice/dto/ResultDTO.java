package com.engineeringthesis.visitservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {
    private Long resultId;
    private String resultName;
    private String resultDescription;
    private Integer resultValue;
    private String doctorNote;
    private Long medicalExaminationId;
}
