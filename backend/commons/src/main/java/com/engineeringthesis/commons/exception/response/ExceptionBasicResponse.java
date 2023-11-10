package com.engineeringthesis.commons.exception.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Schema(name = "ExceptionBasicResponse")
@RequiredArgsConstructor
public class ExceptionBasicResponse {
    private final HttpStatus status;
    private final String reason;
    private final String message;
}
