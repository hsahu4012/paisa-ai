package com.paisaai.backend.service.impl;

import com.paisaai.backend.dto.request.RegisterRequest;
import com.paisaai.backend.dto.response.UserResponse;
import com.paisaai.backend.entity.UserEntity;
import com.paisaai.backend.exception.DuplicateResourceException;
import com.paisaai.backend.mapper.UserMapper;
import com.paisaai.backend.repository.UserRepository;
import com.paisaai.backend.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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

            log.info("User registered successfully with id: {}", savedUser.getId());

            return userMapper.toResponse(savedUser);

        } catch (DuplicateResourceException ex) {

            log.error("Registration failed. Email already exists: {}",
                    request.getEmail());
            throw ex;

        } catch (Exception ex) {

            log.error("Unexpected error while registering user: {}",
                    request.getEmail(), ex);
            throw new RuntimeException("Failed to register user");
        }
    }
}