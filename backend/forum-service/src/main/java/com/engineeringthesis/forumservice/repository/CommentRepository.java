package com.engineeringthesis.forumservice.repository;

import com.engineeringthesis.forumservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsByCommentId(Long commentId);

    void deleteByCommentId(Long commentId);

    Comment save(Comment comment);

    Optional<Comment> findByCommentId(Long commentId);

    Optional<List<Comment>> findAllByThreadIdReferenceOrderByCreationDateAsc(Long threadId);
}
