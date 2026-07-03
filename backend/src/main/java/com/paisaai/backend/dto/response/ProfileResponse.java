package com.paisaai.backend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProfileResponse {

    private Long id;

    private String fullName;

    private String email;

    private BigDecimal salary;

    private String profession;

    private String currency;
}