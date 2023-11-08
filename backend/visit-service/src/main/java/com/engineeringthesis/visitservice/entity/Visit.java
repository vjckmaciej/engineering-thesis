package com.engineeringthesis.visitservice.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visitId", nullable = false)
    private Long visitId;

    @Column
    private LocalDateTime visitDate;

    @Column
    private VisitStatus visitStatus;

    @Column
    private String doctorRecommendations;
}
