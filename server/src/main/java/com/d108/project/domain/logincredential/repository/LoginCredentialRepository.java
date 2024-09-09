package com.d108.project.domain.logincredential.repository;

import com.d108.project.domain.logincredential.domain.LoginCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginCredentialRepository extends JpaRepository<LoginCredential, Integer> {
    Optional<LoginCredential> findByUsername(String username);
}
