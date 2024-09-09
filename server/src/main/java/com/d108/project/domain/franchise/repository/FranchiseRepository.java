package com.d108.project.domain.franchise.repository;

import com.d108.project.domain.franchise.entity.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseRepository extends JpaRepository<Franchise, Long> {
}
