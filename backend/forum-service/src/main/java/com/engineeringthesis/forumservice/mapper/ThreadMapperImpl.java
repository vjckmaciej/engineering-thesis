package com.engineeringthesis.forumservice.mapper;

import com.engineeringthesis.commons.dto.forum.ThreadDTO;
import com.engineeringthesis.forumservice.entity.ForumUser;
import com.engineeringthesis.forumservice.entity.Thread;
import com.engineeringthesis.forumservice.repository.ForumUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ThreadMapperImpl implements ThreadMapper {
    private final ForumUserRepository forumUserRepository;

    @Override
    public Thread threadDTOToThread(ThreadDTO threadDTO) {
        if (threadDTO == null) {
            return null;
        }

        Thread thread = new Thread();

        thread.setThreadId(threadDTO.getThreadId());
        thread.setCategory(threadDTO.getCategory());
        thread.setTitle(threadDTO.getTitle());
        thread.setContent(threadDTO.getContent());

        if (threadDTO.getAuthorId() != null) {
            Optional<ForumUser> optionalForumUser = forumUserRepository.findByForumUserId(threadDTO.getAuthorId());
            if (optionalForumUser.isPresent()) {
                ForumUser forumUser = optionalForumUser.get();
                thread.setAuthor(forumUser);
            } else {
                String message = String.format("Forum User with given forumUserId: %d doesn't exist in database!", threadDTO.getAuthorId());
                log.error(message);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            }
        }

        return thread;
    }

    @Override
    public ThreadDTO threadToThreadDTO(Thread thread) {
        if (thread == null) {
            return null;
        }

        ThreadDTO threadDTO = new ThreadDTO();

        threadDTO.setThreadId(thread.getThreadId());
        threadDTO.setCategory(thread.getCategory());
        threadDTO.setTitle(thread.getTitle());
        threadDTO.setContent(thread.getContent());

        if (thread.getAuthor() != null) {
            threadDTO.setAuthorId(thread.getAuthor().getForumUserId());
        }

        return threadDTO;
    }
}
