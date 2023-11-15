package com.engineeringthesis.visitservice.repository;

import com.engineeringthesis.visitservice.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    Visit save(Visit visit);

    Optional<Visit> findByVisitId(Long visitId);

    boolean existsByVisitId(Long visitId);

    List<Visit> findAll();

    List<Visit> findAllByPatientPesel(String patientPesel);

    List<Visit> deleteByVisitId(Long visitId);
}
