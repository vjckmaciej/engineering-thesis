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
//    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<ExceptionResponse> handleResponseStatusException(ResponseStatusException ex) {
//        ExceptionResponse response = new ExceptionResponse((HttpStatus) ex.getStatusCode(), ex.getReason());
//        return new ResponseEntity<>(response, ex.getStatusCode());
//    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ExceptionResponse> handleException(GlobalException e) {
        ExceptionResponse data = e.getData();
        return new ResponseEntity<>(data, data.getStatus());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionBasicResponse> handleBasicException(ResponseStatusException e) {
//        ExceptionBasicResponse data = new ExceptionBasicResponse((HttpStatus) e.getStatusCode(), e.getReason(), e.getMessage());
        ExceptionBasicResponse data = new ExceptionBasicResponse((HttpStatus) e.getStatusCode(), e.getReason(), e.getMessage());
        return new ResponseEntity<>(data, data.getStatus());
    }


//    @Data
//    static class ExceptionResponse {
//        private final LocalDateTime timestamp;
//        private final HttpStatus status;
//        private final String error;
//
//        public ExceptionResponse(LocalDateTime timestamp, HttpStatus status, String error) {
//            this.timestamp = timestamp;
//            this.status = status;
//            this.error = error;
//        }
//    }
}
