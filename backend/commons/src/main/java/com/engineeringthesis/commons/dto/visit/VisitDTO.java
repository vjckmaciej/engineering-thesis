package com.engineeringthesis.commons.dto.visit;

import com.engineeringthesis.commons.model.visit.VisitStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitDTO {
    private Long visitId;

    @NotBlank(message = "Patient PESEL must not be blank!")
    @Pattern(regexp = "\\d{11}", message = "Patient PESEL must be a string of 11 digits!")
    private String patientPesel;

    @NotBlank(message = "Doctor PESEL must not be blank!")
    @Pattern(regexp = "\\d{11}", message = "Doctor PESEL must be a string of 11 digits!")
    private String doctorPesel;

    @NotNull(message = "Visit date must not be null!")
    @FutureOrPresent(message = "Visit date must be in the future or present!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime visitDate;

    private VisitStatus visitStatus;

//    @NotNull(message = "Week of pregnancy must not be null!")
//    @Min(value = 1, message = "Pregnancy week must be minimum 1!")
//    @Max(value = 42, message = "Pregnancy week must be maximum 42!")
    private Integer weekOfPregnancy;

//    @NotNull(message = "Woman age must not be null!")
//    @Min(value = 18, message = "Woman age must be at least 18!")
    private Integer womanAge;

    private String doctorRecommendations;
}
