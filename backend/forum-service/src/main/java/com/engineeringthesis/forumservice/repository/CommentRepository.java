package com.engineeringthesis.forumservice.repository;

import com.engineeringthesis.forumservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsByCommentId(Long commentId);

    void deleteByCommentId(Long commentId);

    Comment save(Comment comment);

    Optional<Comment> findByCommentId(Long commentId);
}
