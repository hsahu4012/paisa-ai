package com.paisaai.backend.repository;

import com.paisaai.backend.entity.UserSecurityAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSecurityAnswerRepository extends JpaRepository<UserSecurityAnswerEntity, Long> {
}