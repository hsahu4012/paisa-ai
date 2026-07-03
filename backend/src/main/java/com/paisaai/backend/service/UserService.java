package com.paisaai.backend.service;

import com.paisaai.backend.dto.request.RegisterRequest;
import com.paisaai.backend.dto.response.UserResponse;

public interface UserService {

    UserResponse register(RegisterRequest request);
}