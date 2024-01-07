package com.engineeringthesis.commons.dto.visit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultsReportDTO {
    @NotBlank(message = "Result name must not be null!")
    @Size(min = 3, max = 100, message = "Result name must contain minimum of 3 characters and maximum of 100 characters!")
    private String resultName;

    @Size(max = 300, message = "Result description must contain maximum of 300 characters!")
    private String resultDescription;

    private Float numericalResult;

    @Size(max = 50, message = "Unit must contain maximum of 50 characters!")
    private String unit;

    @Size(max = 500, message = "Descriptive result must contain maximum of 500 characters!")
    private String descriptiveResult;

    @Size(max = 1000, message = "Doctor note must contain maximum of 1000 characters!")
    private String doctorNote;
}
