package com.paisaai.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {

    @NotBlank
    private String resetToken;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;
}
