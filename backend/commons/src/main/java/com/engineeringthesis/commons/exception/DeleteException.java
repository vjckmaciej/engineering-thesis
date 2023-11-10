package com.engineeringthesis.commons.exception;

import com.engineeringthesis.commons.exception.response.ExceptionCode;
import org.springframework.http.HttpStatus;

public class DeleteException extends GlobalException {
    private final static String code = String.valueOf(ExceptionCode.DATA_DELETE_001);
    private final static HttpStatus status = HttpStatus.BAD_REQUEST;

    public DeleteException(String message) {
        super(code, message, status);
    }
}
