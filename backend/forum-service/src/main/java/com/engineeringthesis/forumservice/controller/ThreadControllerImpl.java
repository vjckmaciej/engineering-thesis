package com.engineeringthesis.forumservice.controller;

import com.engineeringthesis.commons.model.CrudController;
import com.engineeringthesis.commons.model.CrudResponse;
import com.engineeringthesis.commons.dto.forum.ThreadDTO;
import com.engineeringthesis.forumservice.mapper.ThreadMapper;
import com.engineeringthesis.forumservice.entity.Thread;
import com.engineeringthesis.forumservice.service.ThreadServiceImpl;
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
@RequestMapping(path = "/thread")
public class ThreadControllerImpl implements CrudController<ThreadDTO>{
    private final ThreadServiceImpl threadService;
    private final ThreadMapper threadMapper;

    @Override
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> add(@Valid @RequestBody ThreadDTO threadDTO) {
        Long threadId = threadDTO.getThreadId();
        log.info("Starting saving Thread with threadId: " + threadId);
        Thread threadToSave = threadMapper.threadDTOToThread(threadDTO);
        threadService.save(threadToSave);
        return ResponseEntity.ok(new CrudResponse(threadToSave.getThreadId(), "Thread added to database!"));

    }

    @Override
    @RequestMapping(path = "/{threadId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ThreadDTO> getById(@PathVariable Long threadId) {
        log.info("Starting finding Thread with threadId: " + threadId);
        Thread thread = threadService.getById(threadId);
        ThreadDTO threadDTO = threadMapper.threadToThreadDTO(thread);
        return ResponseEntity.ok(threadDTO);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ThreadDTO>> getAll() {
        log.info("Starting getting list of all Thread objects");
        List<Thread> allThreads = threadService.getAll();
        List<ThreadDTO> allThreadDTOS = allThreads.stream().map((threadMapper::threadToThreadDTO)).collect(Collectors.toList());
        return ResponseEntity.ok(allThreadDTOS);
    }

    @Override
    @RequestMapping(method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> update(@RequestBody ThreadDTO threadDTO) {
        Long threadId = threadDTO.getThreadId();
        log.info(String.format("Starting Thread update with threadId: %d", threadId));
        Thread thread = threadMapper.threadDTOToThread(threadDTO);
        threadService.update(thread);
        return ResponseEntity.ok(new CrudResponse(threadId, "Thread updated!"));
    }

    @Override
    @RequestMapping(path = "/{threadId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> deleteById(@PathVariable Long threadId) {
        log.info(String.format("Starting deleting Thread by threadId: %d", threadId));
        threadService.deleteById(threadId);
        String message = String.format("Thread with threadId: %d deleted!", threadId);
        return ResponseEntity.ok(new CrudResponse(threadId, message));
    }
}
