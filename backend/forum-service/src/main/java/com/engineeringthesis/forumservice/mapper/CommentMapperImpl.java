package com.engineeringthesis.forumservice.mapper;

import com.engineeringthesis.commons.dto.forum.CommentDTO;
import com.engineeringthesis.forumservice.entity.Comment;
import com.engineeringthesis.forumservice.entity.ForumUser;
import com.engineeringthesis.forumservice.entity.Thread;
import com.engineeringthesis.forumservice.repository.ForumUserRepository;
import com.engineeringthesis.forumservice.repository.ThreadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentMapperImpl implements CommentMapper {
    private final ThreadRepository threadRepository;
    private final ForumUserRepository forumUserRepository;


    @Override
    public Comment commentDTOToComment(CommentDTO commentDTO) {
        if (commentDTO == null) {
            return null;
        }

        Comment comment = new Comment();

        comment.setCommentId(commentDTO.getCommentId());
        comment.setContent(commentDTO.getContent());

        if (commentDTO.getThreadId() != null) {
            Optional<Thread> optionalThread = threadRepository.findByThreadId(commentDTO.getThreadId());
            if (optionalThread.isPresent()) {
                Thread thread = optionalThread.get();
                comment.setThread(thread);
                comment.setThreadIdReference(commentDTO.getThreadId());
            } else {
                String message = String.format("Thread with given threadId: %d doesn't exist in database!", commentDTO.getThreadId());
                log.error(message);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            }
        }

        if (commentDTO.getAuthorId() != null) {
            Optional<ForumUser> optionalForumUser = forumUserRepository.findByForumUserId(commentDTO.getAuthorId());
            if (optionalForumUser.isPresent()) {
                ForumUser forumUser = optionalForumUser.get();
                comment.setAuthor(forumUser);
            } else {
                String message = String.format("Forum User with given forumUserId: %d doesn't exist in database!", commentDTO.getAuthorId());
                log.error(message);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            }
        }

        return comment;
    }

    @Override
    public CommentDTO commentToCommentDTO(Comment comment) {
        if (comment == null) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setCommentId(comment.getCommentId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreationDate(comment.getCreationDate());

        if (comment.getThread() != null) {
            commentDTO.setThreadId(comment.getThread().getThreadId());
        }

        if (comment.getAuthor() != null) {
            commentDTO.setAuthorId(comment.getAuthor().getForumUserId());
        }

        return commentDTO;
    }
}
