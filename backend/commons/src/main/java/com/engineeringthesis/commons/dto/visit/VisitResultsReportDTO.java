package com.engineeringthesis.commons.dto.visit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitResultsReportDTO {
    private LocalDateTime visitDate;
    private Integer weekOfPregnancy;
    private Integer womanAge;
    private Map<String, List<ResultsReportDTO>> results = new HashMap<>();
    private String doctorRecommendations;
}
