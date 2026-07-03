package com.paisaai.backend.service.impl;

import com.paisaai.backend.dto.request.LoginRequest;
import com.paisaai.backend.dto.request.RegisterRequest;
import com.paisaai.backend.dto.request.SecurityAnswerRequest;
import com.paisaai.backend.dto.response.LoginResponse;
import com.paisaai.backend.dto.response.LoginUserResponse;
import com.paisaai.backend.dto.response.SecurityQuestionResponse;
import com.paisaai.backend.dto.response.UserResponse;
import com.paisaai.backend.entity.SecurityQuestionEntity;
import com.paisaai.backend.entity.UserEntity;
import com.paisaai.backend.entity.UserSecurityAnswerEntity;
import com.paisaai.backend.entity.UserStatus;
import com.paisaai.backend.exception.DuplicateResourceException;
import com.paisaai.backend.exception.InvalidCredentialsException;
import com.paisaai.backend.exception.UserInactiveException;
import com.paisaai.backend.exception.ValidationException;
import com.paisaai.backend.mapper.UserMapper;
import com.paisaai.backend.repository.SecurityQuestionRepository;
import com.paisaai.backend.repository.UserRepository;
import com.paisaai.backend.repository.UserSecurityAnswerRepository;
import com.paisaai.backend.security.JwtService;
import com.paisaai.backend.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final SecurityQuestionRepository securityQuestionRepository;
    private final UserSecurityAnswerRepository userSecurityAnswerRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional(readOnly = true)
    public List<SecurityQuestionResponse> getActiveSecurityQuestions() {

        log.info("Fetching active security questions");

        return securityQuestionRepository
                .findByIsActiveTrue()
                .stream()
                .map(question -> new SecurityQuestionResponse(
                        question.getId(),
                        question.getQuestion()
                ))
                .toList();
    }
    
    @Override
    @Transactional
    public UserResponse register(RegisterRequest request) {

        try {

            log.info("Register request received for email: {}", request.getEmail());

            if (userRepository.existsByEmail(request.getEmail())) {
                log.warn("Duplicate email found: {}", request.getEmail());
                throw new DuplicateResourceException("Email already exists");
            }

            UserEntity user = userMapper.toEntity(request);

            user.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );

            UserEntity savedUser = userRepository.save(user);

            if (request.getSecurityAnswers() != null
                    && !request.getSecurityAnswers().isEmpty()) {

                for (SecurityAnswerRequest answerRequest : request.getSecurityAnswers()) {

                    SecurityQuestionEntity question =
                            securityQuestionRepository
                                    .findByIdAndIsActiveTrue(answerRequest.getQuestionId())
                                    .orElseThrow(() ->
                                            new ValidationException(
                                                    "Invalid security question id: "
                                                            + answerRequest.getQuestionId()
                                            ));

                    String normalizedAnswer = answerRequest.getAnswer()
                            .trim()
                            .toLowerCase();

                    UserSecurityAnswerEntity userSecurityAnswer =
                            UserSecurityAnswerEntity.builder()
                                    .user(savedUser)
                                    .securityQuestion(question)
                                    .answer(
                                            passwordEncoder.encode(normalizedAnswer)
                                    )
                                    .build();

                    userSecurityAnswerRepository.save(userSecurityAnswer);
                }
            }

            log.info("User registered successfully with id: {}", savedUser.getId());

            return userMapper.toResponse(savedUser);

        } catch (DuplicateResourceException | ValidationException ex) {

            log.error("Registration failed for email: {}", request.getEmail());
            throw ex;

        } catch (Exception ex) {

            log.error(
                    "Unexpected error while registering user: {}",
                    request.getEmail(),
                    ex
            );

            throw new RuntimeException("Failed to register user");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        try {

            log.info("Login attempt for email: {}", request.getEmail());

            UserEntity user = userRepository
                    .findByEmail(request.getEmail())
                    .orElseThrow(() -> {
                        log.warn("Invalid email: {}", request.getEmail());
                        return new InvalidCredentialsException(
                                "Invalid email or password");
                    });

            if (!passwordEncoder.matches(
                    request.getPassword(),
                    user.getPassword())) {

                log.warn("Invalid password for email: {}",
                        request.getEmail());

                throw new InvalidCredentialsException(
                        "Invalid email or password");
            }

            if (user.getStatus() != UserStatus.ACTIVE) {

                log.warn("Inactive account login attempt: {}",
                        request.getEmail());

                throw new UserInactiveException(
                        "Account is inactive. Please contact support."
                );
            }

            LoginUserResponse loginUser =
                    LoginUserResponse.builder()
                            .id(user.getId())
                            .fullName(user.getFullName())
                            .email(user.getEmail())
                            .status(user.getStatus())
                            .build();

            log.info("Login successful for email: {}",
                    request.getEmail());
            
            String token =
                    jwtService.generateToken(
                            user.getEmail()
                    );

            return LoginResponse.builder()
                    .user(loginUser)
                    .token(token)
                    .build();

        } catch (InvalidCredentialsException ex) {
            throw ex;

        } catch (Exception ex) {

            log.error("Login failed for email: {}",
                    request.getEmail(), ex);

            throw ex;
        }
    }
    
    
}