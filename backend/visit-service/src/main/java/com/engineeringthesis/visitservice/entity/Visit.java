package com.engineeringthesis.visitservice.entity;

import com.engineeringthesis.visitservice.model.VisitStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


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

    @Enumerated(EnumType.STRING)
    @Column
    private VisitStatus visitStatus;

    @Column
    private String doctorRecommendations;

    @Column(name = "doctorId")
    private Long doctorId;

    @Column(name = "patientId")
    private Long patientId;

    @OneToMany(mappedBy = "visit")
    private List<MedicalExamination> medicalExaminations;
}
