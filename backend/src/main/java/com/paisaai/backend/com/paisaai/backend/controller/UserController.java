package com.paisaai.backend.com.paisaai.backend.controller;

import com.paisaai.backend.com.paisaai.backend.dto.request.RegisterRequest;
import com.paisaai.backend.com.paisaai.backend.dto.response.ApiResponse;
import com.paisaai.backend.com.paisaai.backend.dto.response.UserResponse;
import com.paisaai.backend.com.paisaai.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse response = userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "User registered successfully",
                        response
                ));
    }
}