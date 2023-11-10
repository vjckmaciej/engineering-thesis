package com.engineeringthesis.commons.exception.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Schema(name = "ExceptionResponse")
@RequiredArgsConstructor
public class ExceptionResponse {
    private final String errorId;
    private final String code;
    private final String message;
    private final HttpStatus status;
}
