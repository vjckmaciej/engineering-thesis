package com.engineeringthesis.commons.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CrudPESELResponse {
    private final String pesel;
    private final String message;
}