package com.paisaai.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SecurityAnswerRequest {

    @NotNull(message = "Question id is required")
    private Long questionId;

    @NotBlank(message = "Answer is required")
    private String answer;
}