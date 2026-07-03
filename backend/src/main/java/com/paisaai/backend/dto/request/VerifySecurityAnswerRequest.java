package com.paisaai.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class VerifySecurityAnswerRequest {

    @NotBlank
    @Email
    private String email;

    private List<SecurityAnswerRequest> answers;
}
