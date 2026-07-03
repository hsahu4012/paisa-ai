package com.paisaai.backend.service;

import java.util.List;

import com.paisaai.backend.dto.request.RegisterRequest;
import com.paisaai.backend.dto.response.SecurityQuestionResponse;
import com.paisaai.backend.dto.response.UserResponse;

public interface UserService {

    UserResponse register(RegisterRequest request);
    List<SecurityQuestionResponse> getActiveSecurityQuestions();
}