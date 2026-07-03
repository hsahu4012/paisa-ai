package com.paisaai.backend.controller;

import com.paisaai.backend.dto.request.LoginRequest;
import com.paisaai.backend.dto.request.RegisterRequest;
import com.paisaai.backend.dto.response.ApiResponse;
import com.paisaai.backend.dto.response.LoginResponse;
import com.paisaai.backend.dto.response.SecurityQuestionResponse;
import com.paisaai.backend.dto.response.UserResponse;
import com.paisaai.backend.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationService authenticationService;
    
    @GetMapping("/security-questions")
    public ResponseEntity<ApiResponse<List<SecurityQuestionResponse>>> getSecurityQuestions() {

        List<SecurityQuestionResponse> questions =
                authenticationService.getActiveSecurityQuestions();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Security questions fetched successfully",
                        questions
                )
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest request) {
    	log.info("<<START>> register");

        UserResponse response = authenticationService.register(request);
        log.info("<<END>> register");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "User registered successfully",
                        response
                ));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authenticationService.login(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Login successful",
                        response
                )
        );
    }
}