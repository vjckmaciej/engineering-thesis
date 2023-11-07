package com.engineeringthesis.visitservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitDTO {
    private Long visitId;
    private LocalDateTime visitDate;
    private String doctorRecommendations;
}
