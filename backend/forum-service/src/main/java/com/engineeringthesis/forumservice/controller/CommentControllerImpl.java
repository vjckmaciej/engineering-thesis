package com.engineeringthesis.forumservice.controller;

import com.engineeringthesis.commons.dto.forum.CommentDTO;
import com.engineeringthesis.commons.model.CrudController;
import com.engineeringthesis.commons.model.CrudResponse;
import com.engineeringthesis.forumservice.entity.Comment;
import com.engineeringthesis.forumservice.mapper.CommentMapper;
import com.engineeringthesis.forumservice.service.CommentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/comment")
public class CommentControllerImpl implements CrudController<CommentDTO> {
    private final CommentServiceImpl commentService;
    private final CommentMapper commentMapper;


    @Override
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> add(@Valid @RequestBody CommentDTO commentDTO) {
        Long commentId = commentDTO.getCommentId();
        log.info("Starting saving Comment with commentId: " + commentId);
        Comment commentToSave = commentMapper.commentDTOToComment(commentDTO);
        commentService.save(commentToSave);
        return ResponseEntity.ok(new CrudResponse(commentToSave.getCommentId(), "Comment added to database!"));
    }

    @Override
    @RequestMapping(path = "/comment/{commentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDTO> getById(@PathVariable Long commentId) {
        log.info("Starting finding Comment with commentId: " + commentId);
        Comment comment = commentService.getById(commentId);
        CommentDTO commentDTO = commentMapper.commentToCommentDTO(comment);
        return ResponseEntity.ok(commentDTO);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommentDTO>> getAll() {
        log.info("Starting getting list of all Comment objects");
        List<Comment> allComments = commentService.getAll();
        List<CommentDTO> allCommentDTOS = allComments.stream().map((commentMapper::commentToCommentDTO)).collect(Collectors.toList());
        return ResponseEntity.ok(allCommentDTOS);
    }

    @RequestMapping(path = "/{threadId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommentDTO>> getAllCommentsByThreadId(@PathVariable Long threadId) {
        log.info("Starting getting list of all Comment objects for thread with threadId:" + threadId);
        List<Comment> allComments = commentService.getAllCommentsByThreadId(threadId);
        List<CommentDTO> allCommentDTOS = allComments.stream().map((commentMapper::commentToCommentDTO)).collect(Collectors.toList());
        return ResponseEntity.ok(allCommentDTOS);
    }

    @Override
    @RequestMapping(method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> update(@RequestBody CommentDTO commentDTO) {
        Long commentId = commentDTO.getCommentId();
        log.info(String.format("Starting Comment update with commentId: %d", commentId));
        Comment comment = commentMapper.commentDTOToComment(commentDTO);
        commentService.update(comment);
        return ResponseEntity.ok(new CrudResponse(commentId, "Comment updated!"));
    }

    @Override
    @RequestMapping(path = "/{commentId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> deleteById(@PathVariable Long commentId) {
        log.info(String.format("Starting deleting Comment by commentId: %d", commentId));
        commentService.deleteById(commentId);
        String message = String.format("Comment with commentId: %d deleted!", commentId);
        return ResponseEntity.ok(new CrudResponse(commentId, message));
    }
}
