package com.paisaai.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SecurityQuestionResponse {

    private Long id;
    private String question;
}
