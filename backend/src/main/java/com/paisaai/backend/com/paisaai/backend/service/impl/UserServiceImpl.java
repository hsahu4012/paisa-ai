package com.paisaai.backend.com.paisaai.backend.service.impl;

import com.paisaai.backend.com.paisaai.backend.dto.request.RegisterRequest;
import com.paisaai.backend.com.paisaai.backend.dto.response.UserResponse;
import com.paisaai.backend.com.paisaai.backend.entity.UserEntity;
import com.paisaai.backend.com.paisaai.backend.exception.DuplicateResourceException;
import com.paisaai.backend.com.paisaai.backend.mapper.UserMapper;
import com.paisaai.backend.com.paisaai.backend.repository.UserRepository;
import com.paisaai.backend.com.paisaai.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        UserEntity user = userMapper.toEntity(request);

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        UserEntity savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }
}