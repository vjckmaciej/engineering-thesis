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

    Optional<Doctor> findByPesel(String pesel);

    boolean existsByDoctorId(Long doctorId);

    boolean existsByPesel(String PESEL);

    List<Doctor> findAll();

    List<Doctor> deleteByDoctorId(Long doctorId);

    void deleteByPesel(String pesel);
}
