package com.d108.project.domain.logincredential.domain;

import com.d108.project.domain.common.BaseTimeEntity;
import com.d108.project.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "login_credentials")
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED) // 상속 전략 설정
public class LoginCredential extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;

    @Builder
    public LoginCredential(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
