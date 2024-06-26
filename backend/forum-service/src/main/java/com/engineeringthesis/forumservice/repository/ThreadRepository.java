package com.engineeringthesis.forumservice.repository;

import com.engineeringthesis.forumservice.entity.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {
    boolean existsByThreadId(Long threadId);

    void deleteByThreadId(Long threadId);

    Thread save(Thread thread);

    Optional<Thread> findByThreadId(Long threadId);

    List<Thread> findAllByCategory(String category);

    Optional<List<Thread>> findAllByAuthorIdReferenceOrderByCreationDateAsc(Long authorId);
}
