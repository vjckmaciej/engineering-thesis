package com.engineeringthesis.visitservice.dto;

import com.engineeringthesis.visitservice.model.VisitStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitDTO {
    private Long visitId;
//    private Long patientId;
//    private Long doctorId;
    private String patientPesel;
    private String doctorPesel;
    private LocalDateTime visitDate;
    private VisitStatus visitStatus;
    private Integer weekOfPregnancy;
    private String doctorRecommendations;
}
