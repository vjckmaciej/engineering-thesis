package com.engineeringthesis.visitservice.repository;

import com.engineeringthesis.visitservice.entity.MedicalExamination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalExaminationRepository extends JpaRepository<MedicalExamination, Long> {
    MedicalExamination save(MedicalExamination medicalExamination);

    Optional<MedicalExamination> findByMedicalExaminationId(Long medicalExaminationId);

    boolean existsByMedicalExaminationId(Long medicalExaminationId);

    List<MedicalExamination> findAll();

    List<MedicalExamination> deleteByMedicalExaminationId(Long medicalExaminationId);
}
