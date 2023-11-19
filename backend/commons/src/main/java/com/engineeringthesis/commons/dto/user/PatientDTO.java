package com.engineeringthesis.commons.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private Long patientId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String pesel;
    private String phoneNumber;
    private Long doctorId;
}
