package com.engineeringthesis.commons.dto.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private Long patientId;

    @NotBlank(message = "First name must not be blank!")
    @Size(max = 50, message = "First name must contain maximum of 50 characters!")
    private String firstName;

    @NotBlank(message = "Last name must not be blank!")
    @Size(max = 50, message = "Last name must contain maximum of 100 characters!")
    private String lastName;

    @Past(message = "Birth date must be set to past!")
    private LocalDate birthDate;

    @PastOrPresent(message = "Pregnancy start date must be in the past or present!")
    private LocalDate pregnancyStartDate;

    @Pattern(regexp = "\\d{11}", message = "PESEL must contain 11 digits!")
    private String pesel;

    @Pattern(regexp = "\\d{9}", message = "Phone number must contain 9 digits!")
    private String phoneNumber;

//    @NotNull(message = "DoctorID must not be null!")
//    @Positive(message = "DoctorID must be a positive number!")
    private Long doctorId;
}
