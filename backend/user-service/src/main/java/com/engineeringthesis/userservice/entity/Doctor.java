package com.engineeringthesis.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DOCTORS")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "doctorId", nullable = false)
    private Long doctorId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private LocalDate birthDate;

    @Column
    private String pesel;

    @Column
    private String phoneNumber;

    @Column(updatable = false)
    private LocalDate registryDate;

    @OneToMany(mappedBy = "pregnancyDoctor")
    private List<Patient> patients;
}
