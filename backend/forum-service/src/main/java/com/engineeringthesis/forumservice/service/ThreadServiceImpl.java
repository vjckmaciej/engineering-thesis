package com.engineeringthesis.forumservice.service;

import com.engineeringthesis.commons.exception.DataSaveException;
import com.engineeringthesis.commons.exception.DeleteException;
import com.engineeringthesis.commons.model.CrudService;
import com.engineeringthesis.forumservice.repository.ThreadRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.engineeringthesis.forumservice.entity.Thread;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThreadServiceImpl  implements CrudService<Thread> {
    private final ThreadRepository threadRepository;


    @Override
    public void save(Thread thread) {
        Long threadId = thread.getThreadId();
        try {
            if (threadRepository.existsByThreadId(threadId)) {
                String message = String.format("Thread with this threadId: %d already exists in database!", threadId);
                log.error(message);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
            }

            threadRepository.save(thread);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save Thread with this threadId: %d", threadId);
            throw new DataSaveException(message);
        }
    }

    @Override
    public Thread getById(Long id) {
        String message = String.format("Thread with this threadId: %d doesn't exist in database!", id);
        return threadRepository.findByThreadId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, message));
    }

    @Override
    public List<Thread> getAll() {
        return threadRepository.findAll();
    }

    public List<Thread> getAllThreadsByCategory(String category) {
        return threadRepository.findAllByCategory(category);
    }

    public List<Thread> getAllThreadsByAuthorId(Long authorId) {
        String message = String.format("Threads with this authorID: %d don't exist in database!", authorId);
        return threadRepository.findAllByAuthorIdReferenceOrderByCreationDateAsc(authorId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, message));
    }

    @Override
    public void update(Thread thread) {
        Long threadId = thread.getThreadId();
        try {
            Thread oldThread = threadRepository.findByThreadId(threadId).orElseThrow(() -> {
                String message = String.format("%s with this threadId: %d doesn't exist in database!", thread.getClass().getName(), threadId);
//                return new EntityIdDoesNotExistException(message);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            });

            thread.setThreadId(oldThread.getThreadId());

            threadRepository.save(thread);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save Thread with this threadId: %d", threadId);
            throw new DataSaveException(message);
        }
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            if (!threadRepository.existsByThreadId(id)) {
                String message = String.format("Cannot delete Thread with threadId: %d! Object doesn't exist in database!", id);
                //            throw new DeleteException(message);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            }
            threadRepository.deleteByThreadId(id);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot delete Thread with this threadId: %d", id);
            throw new DeleteException(message);
        }
    }
}
