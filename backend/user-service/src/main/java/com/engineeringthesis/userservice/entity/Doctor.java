package com.engineeringthesis.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DOCTORS")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctorId", nullable = false)
    private Long doctorId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String phoneNumber;

    @OneToMany(mappedBy = "prenatalDoctor")
    private List<Patient> patients;
}
