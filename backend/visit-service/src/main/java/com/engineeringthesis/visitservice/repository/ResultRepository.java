package com.engineeringthesis.visitservice.repository;

import com.engineeringthesis.visitservice.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    Result save(Result result);

    Optional<Result> findByResultId(Long resultId);

    boolean existsByResultId(Long resultId);

    List<Result> findAll();

    Optional<List<Result>> findAllByMedicalExaminationIdReference(Long visitId);

    List<Result> deleteByResultId(Long resultId);
}
