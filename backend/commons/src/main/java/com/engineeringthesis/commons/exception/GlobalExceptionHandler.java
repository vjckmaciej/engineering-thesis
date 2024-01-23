package com.engineeringthesis.commons.exception;

import com.engineeringthesis.commons.exception.response.ExceptionBasicResponse;
import com.engineeringthesis.commons.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ExceptionResponse> handleException(GlobalException e) {
        ExceptionResponse data = e.getData();
        return new ResponseEntity<>(data, data.getStatus());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionBasicResponse> handleBasicException(ResponseStatusException e) {
        ExceptionBasicResponse data = new ExceptionBasicResponse((HttpStatus) e.getStatusCode(), e.getReason(), e.getMessage());
        return new ResponseEntity<>(data, data.getStatus());
    }
}
