package com.engineeringthesis.commons.exception;

import com.engineeringthesis.commons.exception.response.ExceptionResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Getter
public class GlobalException extends RuntimeException {
    private final ExceptionResponse data;

    public GlobalException(String code, String message, HttpStatus status) {
        super(message + status.toString());
        data = createExceptionResponse(code, message, status);
    }

    private ExceptionResponse createExceptionResponse(String code, String message, HttpStatus httpStatus) {
        String errorId = String.valueOf(UUID.randomUUID());
        return new ExceptionResponse(errorId, code, message, httpStatus);
    }
}
