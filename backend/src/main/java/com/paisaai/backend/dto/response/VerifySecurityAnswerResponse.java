package com.paisaai.backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifySecurityAnswerResponse {

    private String resetToken;
}