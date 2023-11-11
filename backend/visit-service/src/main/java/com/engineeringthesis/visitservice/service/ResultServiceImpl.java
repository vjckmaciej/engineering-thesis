package com.engineeringthesis.visitservice.service;

import com.engineeringthesis.commons.exception.DataSaveException;
import com.engineeringthesis.commons.exception.DeleteException;
import com.engineeringthesis.commons.model.CrudService;
import com.engineeringthesis.visitservice.entity.Result;
import com.engineeringthesis.visitservice.repository.ResultRepository;
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
public class ResultServiceImpl implements CrudService<Result> {
    private final ResultRepository resultRepository;

    @Override
    public void save(Result result) {
        Long resultId = result.getResultId();
        try {
            if (resultRepository.existsByResultId(resultId)) {
                String message = String.format("Result with this resultId: %d already exists in database!", resultId);
                log.error(message);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
            }

            resultRepository.save(result);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save Result with this resultId: %d", resultId);
            throw new DataSaveException(message);
        }
    }

    @Override
    public Result getById(Long id) {
        String message = String.format("Result with this resultId: %d doesn't exist in database!", id);
        return resultRepository.findByResultId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, message));
    }

    @Override
    public List<Result> getAll() {
        return resultRepository.findAll();
    }

    @Override
    public void update(Result result) {
        Long resultId = result.getResultId();
        try {
            Result oldResult = resultRepository.findByResultId(resultId).orElseThrow(() -> {
                String message = String.format("%s with this resultId: %d doesn't exist in database!", result.getClass().getName(), resultId);
//                return new EntityIdDoesNotExistException(message);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            });

            result.setResultId(oldResult.getResultId());

            resultRepository.save(result);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save Result with this resultId: %d", resultId);
            throw new DataSaveException(message);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            if (!resultRepository.existsByResultId(id)) {
                String message = String.format("Cannot delete Result with resultId: %d! Object doesn't exist in database!", id);
                //            throw new DeleteException(message);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            }
            resultRepository.deleteByResultId(id);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot delete Result with this resultId: %d", id);
            throw new DeleteException(message);
        }
    }
}
