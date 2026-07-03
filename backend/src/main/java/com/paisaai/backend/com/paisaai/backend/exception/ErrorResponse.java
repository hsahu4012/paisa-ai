package com.paisaai.backend.com.paisaai.backend.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResponse {

    private boolean success;
    private String message;
    private List<String> errors;
}