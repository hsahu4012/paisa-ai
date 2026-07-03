package com.paisaai.backend.dto.response;

import com.paisaai.backend.entity.UserStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserResponse {

    private Long id;
    private String fullName;
    private String email;
    private UserStatus status;
}