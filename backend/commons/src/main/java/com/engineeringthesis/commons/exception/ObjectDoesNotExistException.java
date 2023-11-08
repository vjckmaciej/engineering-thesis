package com.engineeringthesis.commons.exception;

import org.springframework.http.HttpStatus;

public class ObjectDoesNotExistException extends GlobalException {
    private final static String code = String.valueOf(ExceptionCode.DATA_FIND_001);
    private final static HttpStatus status = HttpStatus.NOT_FOUND;

    public ObjectDoesNotExistException(String message) { super(code, message, status);}
}
