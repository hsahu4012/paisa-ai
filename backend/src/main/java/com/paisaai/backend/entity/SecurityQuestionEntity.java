package com.paisaai.backend.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "security_questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityQuestionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question", nullable = false, unique = true)
    private String question;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @JsonIgnore
    @OneToMany(mappedBy = "securityQuestion")
    private List<UserSecurityAnswerEntity> userAnswers;
}