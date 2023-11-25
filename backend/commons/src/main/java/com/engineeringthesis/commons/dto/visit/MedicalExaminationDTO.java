package com.engineeringthesis.commons.dto.visit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalExaminationDTO {
    private Long medicalExaminationId;

    @NotBlank(message = "Medical examination name must not be blank!")
    @Size(min = 3, max = 100, message = "Medical examination name must contain minimum of 3 characters and maximum of 100 characters!")
    private String medicalExaminationName;

    @NotNull(message = "VisitID must not be null!")
    @Positive(message = "VisitID must be a positive number!")
    private Long visitId;
}
