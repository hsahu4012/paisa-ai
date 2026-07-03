package com.paisaai.backend.com.paisaai.backend.mapper;

import com.paisaai.backend.com.paisaai.backend.dto.request.RegisterRequest;
import com.paisaai.backend.com.paisaai.backend.dto.response.UserResponse;
import com.paisaai.backend.com.paisaai.backend.entity.UserEntity;
import com.paisaai.backend.com.paisaai.backend.entity.UserStatus;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(RegisterRequest request) {

    	UserEntity user = new UserEntity();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setSalary(request.getSalary());
        user.setProfession(request.getProfession());
        user.setStatus(UserStatus.ACTIVE);

        return user;
    }

    public UserResponse toResponse(UserEntity user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setSalary(user.getSalary());
        response.setProfession(user.getProfession());
        response.setCreatedAt(user.getCreatedAt());

        return response;
    }
}