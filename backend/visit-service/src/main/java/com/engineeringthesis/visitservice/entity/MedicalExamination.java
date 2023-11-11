package com.engineeringthesis.visitservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MEDICAL EXAMINATIONS")
public class MedicalExamination {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "medicalExaminationId", nullable = false)
    private Long medicalExaminationId;

    @Column
    private String medicalExaminationName;

    @ManyToMany
    @JoinTable(
            name = "MEDICAL_EXAMINATIONS_VISITS",
            joinColumns = @JoinColumn(name = "medicalExaminationId"),
            inverseJoinColumns = @JoinColumn(name = "visitId")
    )
    private List<Visit> visits;

    @OneToMany(mappedBy = "medicalExamination")
    private List<Result> exactResults;
}
