package com.engineeringthesis.forumservice.service;

import com.engineeringthesis.commons.exception.DataSaveException;
import com.engineeringthesis.commons.exception.DeleteException;
import com.engineeringthesis.commons.model.CrudService;
import com.engineeringthesis.forumservice.entity.Comment;
import com.engineeringthesis.forumservice.entity.Comment;
import com.engineeringthesis.forumservice.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CrudService<Comment> {
    private final CommentRepository commentRepository;

    @Override
    public void save(Comment comment) {
        Long commentId = comment.getCommentId();
        try {
            if (commentRepository.existsByCommentId(commentId)) {
                String message = String.format("Comment with this commentId: %d already exists in database!", commentId);
                log.error(message);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
            }
            commentRepository.save(comment);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save Comment with this commentId: %d", commentId);
            throw new DataSaveException(message);
        }
    }

    @Override
    public Comment getById(Long id) {
        String message = String.format("Comment with this commentId: %d doesn't exist in database!", id);
        return commentRepository.findByCommentId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, message));
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public List<Comment> getAllCommentsByThreadId(Long threadId) {
        String message = String.format("Comments with this threadId: %d don't exist in database!", threadId);
        return commentRepository.findAllByThreadIdReferenceOrderByCreationDateAsc(threadId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, message));
    }

    @Override
    public void update(Comment comment) {
        Long commentId = comment.getCommentId();
        try {
            Comment oldComment = commentRepository.findByCommentId(commentId).orElseThrow(() -> {
                String message = String.format("%s with this commentId: %d doesn't exist in database!", comment.getClass().getName(), commentId);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            });

            comment.setCommentId(oldComment.getCommentId());

            commentRepository.save(comment);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save Comment with this commentId: %d", commentId);
            throw new DataSaveException(message);
        }
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            if (!commentRepository.existsByCommentId(id)) {
                String message = String.format("Cannot delete Comment with commentId: %d! Object doesn't exist in database!", id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            }
            commentRepository.deleteByCommentId(id);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot delete Comment with this commentId: %d", id);
            throw new DeleteException(message);
        }
    }
}
