package com.engineeringthesis.commons.exception;

import com.engineeringthesis.commons.exception.response.ExceptionCode;
import org.springframework.http.HttpStatus;

public class EntityIdAlreadyExistException extends GlobalException {
    private final static String code = String.valueOf(ExceptionCode.DATA_SAVE_002);
    private final static HttpStatus status = HttpStatus.BAD_REQUEST;

    public EntityIdAlreadyExistException(String message) {
        super(code, message, status);
    }
}