package com.engineeringthesis.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
    private Long doctorId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String pesel;
    private String phoneNumber;
}
