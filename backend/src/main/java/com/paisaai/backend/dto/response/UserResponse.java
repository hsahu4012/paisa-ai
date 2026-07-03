package com.paisaai.backend.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserResponse {

    private Long id;

    private String fullName;

    private String email;

    private BigDecimal salary;

    private String profession;

    private LocalDateTime createdAt;
}
