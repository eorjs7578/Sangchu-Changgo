package com.d108.project.domain.loginCredential.repository;

import com.d108.project.domain.loginCredential.entity.LoginCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginCredentialRepository extends JpaRepository<LoginCredential, Long> {
    Optional<LoginCredential> findByUsername(String username);
}
