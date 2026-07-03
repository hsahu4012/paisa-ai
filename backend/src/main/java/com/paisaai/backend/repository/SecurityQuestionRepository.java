package com.paisaai.backend.repository;

import com.paisaai.backend.entity.SecurityQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestionEntity, Long> {

    List<SecurityQuestionEntity> findByIsActiveTrue();

    Optional<SecurityQuestionEntity> findByIdAndIsActiveTrue(Long id);
}