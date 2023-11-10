package com.engineeringthesis.visitservice.entity;

import com.engineeringthesis.userservice.entity.Doctor;
import com.engineeringthesis.userservice.entity.Patient;
import com.engineeringthesis.visitservice.model.VisitStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VISITS")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "visitId", nullable = false)
    private Long visitId;

    @Column
    private LocalDateTime visitDate;

    @Column
    private VisitStatus visitStatus;

    @Column
    private String doctorRecommendations;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}
