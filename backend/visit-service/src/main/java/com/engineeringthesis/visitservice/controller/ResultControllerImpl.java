package com.engineeringthesis.visitservice.controller;

import com.engineeringthesis.commons.model.CrudController;
import com.engineeringthesis.commons.model.CrudResponse;
import com.engineeringthesis.commons.dto.visit.ResultDTO;
import com.engineeringthesis.visitservice.entity.Result;
import com.engineeringthesis.visitservice.mapper.ResultMapper;
import com.engineeringthesis.visitservice.service.ResultServiceImpl;
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
@RequestMapping(path = "/result")
public class ResultControllerImpl implements CrudController<ResultDTO> {
    private final ResultServiceImpl resultService;
    private final ResultMapper resultMapper;

    @Override
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> add(@RequestBody ResultDTO resultDTO) {
        Long resultId = resultDTO.getResultId();
        log.info("Starting saving Result with resultId: " + resultId);
        Result result = resultMapper.resultDTOToResult(resultDTO);
        resultService.save(result);
        return ResponseEntity.ok(new CrudResponse(result.getResultId(), "Result added to database!"));
    }

    @Override
    @RequestMapping(path = "/{resultId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultDTO> getById(@PathVariable Long resultId) {
        log.info("Starting finding Result with resultId: " + resultId);
        Result result = resultService.getById(resultId);
        ResultDTO resultDTO = resultMapper.resultToResultDTO(result);
        return ResponseEntity.ok(resultDTO);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResultDTO>> getAll() {
        log.info("Starting getting list of all Result objects");
        List<Result> allResults = resultService.getAll();
        List<ResultDTO> allResultDTOS = allResults.stream().map((resultMapper::resultToResultDTO)).collect(Collectors.toList());
        return ResponseEntity.ok(allResultDTOS);
    }

    @Override
    @RequestMapping(method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> update(@RequestBody ResultDTO resultDTO) {
        Long resultId = resultDTO.getResultId();
        log.info(String.format("Starting Result update with resultId: %d", resultId));
        Result result = resultMapper.resultDTOToResult(resultDTO);
        resultService.update(result);
        return ResponseEntity.ok(new CrudResponse(resultId, "Result updated!"));
    }

    @Override
    @RequestMapping(path = "/{resultId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> deleteById(@PathVariable Long resultId) {
        log.info(String.format("Starting deleting Result by resultId: %d", resultId));
        resultService.deleteById(resultId);
        String message = String.format("Result with resultId: %d deleted!", resultId);
        return ResponseEntity.ok(new CrudResponse(resultId, message));
    }
}
