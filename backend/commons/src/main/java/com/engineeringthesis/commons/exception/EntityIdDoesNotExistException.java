package com.engineeringthesis.commons.exception;

import com.engineeringthesis.commons.exception.response.ExceptionCode;
import org.springframework.http.HttpStatus;

public class EntityIdDoesNotExistException extends GlobalException {
    private final static String code = String.valueOf(ExceptionCode.DATA_UPDATE_001);
    private final static HttpStatus status = HttpStatus.BAD_REQUEST;

    public EntityIdDoesNotExistException(String message) {
        super(code, message, status);
    }
}
