package com.engineeringthesis.visitservice.repository;

import com.engineeringthesis.visitservice.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    Visit save(Visit visit);

    Optional<Visit> findByVisitId(Long visitId);

    boolean existsByVisitId(Long visitId);

    List<Visit> findAll();

    List<Visit> findAllByPatientPeselOrderByVisitDateAsc(String patientPesel);

    List<Visit> findAllByDoctorPeselOrderByVisitDateAsc(String doctorPesel);

    List<Visit> findAllByVisitDate(LocalDateTime visitDate);


    List<Visit> deleteByVisitId(Long visitId);


    @Query("SELECT v FROM Visit v " +
            "WHERE v.patientPesel = :patientPesel " +
            "AND v.visitStatus = 'SCHEDULED' " +
            "AND v.visitDate > CURRENT_TIMESTAMP " +
            "ORDER BY v.visitDate ASC " +
            "LIMIT 1")
    Optional<Visit> findNextScheduledVisitByPatientPesel(@Param("patientPesel") String patientPesel);

    @Query("SELECT v FROM Visit v " +
            "WHERE v.doctorPesel = :doctorPesel " +
            "AND v.visitStatus = 'SCHEDULED' " +
            "AND v.visitDate > CURRENT_TIMESTAMP " +
            "ORDER BY v.visitDate ASC " +
            "LIMIT 1")
    Optional<Visit> findNextScheduledVisitByDoctorPesel(@Param("doctorPesel") String doctorPesel);

}
