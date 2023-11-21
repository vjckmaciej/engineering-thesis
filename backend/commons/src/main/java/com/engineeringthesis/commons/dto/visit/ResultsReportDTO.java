package com.engineeringthesis.commons.dto.visit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultsReportDTO {
    private String resultName;
    private String resultDescription;
    private Integer numericalResult;
    private String unit;
    private String descriptiveResult;
    private String doctorNote;
}
