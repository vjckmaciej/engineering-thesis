package com.engineeringthesis.commons.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CrudResponse {
    private final Long id;
    private final String message;
}
