package com.d108.project.domain.franchise.repository;

import com.d108.project.domain.franchise.entity.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FranchiseRepository extends JpaRepository<Franchise, Long> {


    @Query("SELECT DISTINCT f.type FROM Franchise f")
    List<String> findDistinctByType();
    List<Franchise> findAll();
    @Query("SELECT f FROM Franchise f WHERE f.name like %:name%")
    List<Franchise>findAllByName(String name);
    List<Franchise> findAllByType(String type);
    Optional<Franchise> findById(Long id);
}