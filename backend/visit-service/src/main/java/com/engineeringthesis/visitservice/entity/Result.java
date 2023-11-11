package com.engineeringthesis.visitservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RESULTS")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "resultId", nullable = false)
    private Long resultId;

    @Column
    private String resultName;

    @Column
    private String resultDescription;

    @Column
    private Integer resultValue;

    @Column
    private String doctorNote;

    @ManyToOne
    @JoinColumn(name = "medicalExaminationId")
    private MedicalExamination medicalExamination;
}