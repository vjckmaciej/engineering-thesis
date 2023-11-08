package com.engineeringthesis.userservice.repository;

import com.engineeringthesis.userservice.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>  {
    Patient save(Patient patient);

    Optional<Patient> findByPatientId(Long patientId);

    boolean existsByPatientId(Long patientId);

    List<Patient> findAll();

    List<Patient> deleteByPatientId(Long patientId);
}
