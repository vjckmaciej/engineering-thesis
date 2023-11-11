package com.engineeringthesis.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PATIENTS")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "patientId", nullable = false)
    private Long patientId;

    @Column
    private Integer weekOfPregnancy;

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
    @CreationTimestamp
    private LocalDate registryDate;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor pregnancyDoctor;
}
