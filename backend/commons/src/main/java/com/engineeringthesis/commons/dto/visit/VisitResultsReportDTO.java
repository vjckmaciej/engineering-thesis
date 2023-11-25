package com.engineeringthesis.commons.dto.visit;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitResultsReportDTO {
    private LocalDateTime visitDate;

    @NotNull(message = "Week of pregnancy must not be null!")
    @Min(value = 1, message = "Pregnancy week must be minimum 1!")
    @Max(value = 42, message = "Pregnancy week must be maximum 42!")
    private Integer weekOfPregnancy;

    @NotNull(message = "Woman age must not be null!")
    @Min(value = 18, message = "Woman age must be at least 18!")
    private Integer womanAge;

    private Map<String, List<ResultsReportDTO>> results = new HashMap<>();

    private String doctorRecommendations;
}
