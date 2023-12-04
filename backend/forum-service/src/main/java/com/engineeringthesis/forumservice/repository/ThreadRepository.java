package com.engineeringthesis.forumservice.repository;

import com.engineeringthesis.forumservice.entity.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {
//    Doctor save(Doctor doctor);
////
//    Optional<Doctor> findByPesel(String pesel);
//
    boolean existsByThreadId(Long threadId);
//
//    boolean existsByPesel(String PESEL);
//
//    List<Doctor> findAll();
//
//    List<Doctor> deleteByDoctorId(Long doctorId);
//
    void deleteByThreadId(Long threadId);

    Thread save(Thread thread);

    Optional<Thread> findByThreadId(Long threadId);

    List<Thread> findAllByCategory(String category);
}
