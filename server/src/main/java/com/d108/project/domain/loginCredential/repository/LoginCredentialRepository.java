package com.d108.project.domain.loginCredential.repository;

import com.d108.project.domain.loginCredential.entity.LoginCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LoginCredentialRepository extends JpaRepository<LoginCredential, Long> {
    Optional<LoginCredential> findByUsername(String username);

    @Query("SELECT member.refreshToken FROM LoginCredential member WHERE member.username = :username")
    Optional<String> findRefreshTokenByUsername(String username);
}

