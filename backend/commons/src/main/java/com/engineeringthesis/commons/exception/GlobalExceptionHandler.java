package com.engineeringthesis.commons.exception;

import com.engineeringthesis.commons.model.CrudResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ExceptionResponse> handleException(GlobalException e) {
        ExceptionResponse data = e.getData();
        return new ResponseEntity<>(data, data.getStatus());
    }
}
