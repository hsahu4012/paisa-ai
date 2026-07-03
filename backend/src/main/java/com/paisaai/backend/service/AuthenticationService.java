package com.paisaai.backend.service;

import java.util.List;

import com.paisaai.backend.dto.request.ForgotPasswordRequest;
import com.paisaai.backend.dto.request.LoginRequest;
import com.paisaai.backend.dto.request.RegisterRequest;
import com.paisaai.backend.dto.request.ResetPasswordRequest;
import com.paisaai.backend.dto.request.VerifySecurityAnswerRequest;
import com.paisaai.backend.dto.response.ForgotPasswordResponse;
import com.paisaai.backend.dto.response.LoginResponse;
import com.paisaai.backend.dto.response.ProfileResponse;
import com.paisaai.backend.dto.response.ResetPasswordResponse;
import com.paisaai.backend.dto.response.SecurityQuestionResponse;
import com.paisaai.backend.dto.response.UserResponse;
import com.paisaai.backend.dto.response.VerifySecurityAnswerResponse;

public interface AuthenticationService {

    UserResponse register(RegisterRequest request);
    List<SecurityQuestionResponse> getActiveSecurityQuestions();
    LoginResponse login(LoginRequest request);
    
    List<ForgotPasswordResponse> forgotPassword(ForgotPasswordRequest request);
    VerifySecurityAnswerResponse verifySecurityAnswers(VerifySecurityAnswerRequest request);
    ResetPasswordResponse resetPassword(ResetPasswordRequest request);
    
    ProfileResponse getCurrentUser();
}