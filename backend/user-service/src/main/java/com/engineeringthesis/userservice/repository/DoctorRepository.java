package com.engineeringthesis.userservice.repository;

import com.engineeringthesis.userservice.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor save(Doctor doctor);

    Optional<Doctor> findByDoctorId(Long doctorId);

    boolean existsByDoctorId(Long doctorId);

    List<Doctor> findAll();

    List<Doctor> deleteByDoctorId(Long doctorId);
}
