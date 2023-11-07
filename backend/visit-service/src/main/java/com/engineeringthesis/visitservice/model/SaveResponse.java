package com.engineeringthesis.visitservice.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SaveResponse {
    private final Long visitId;
    private final String message;
}
