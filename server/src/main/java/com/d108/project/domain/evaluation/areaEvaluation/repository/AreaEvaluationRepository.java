package com.d108.project.domain.evaluation.areaEvaluation.repository;

import com.d108.project.domain.evaluation.areaEvaluation.entity.AreaEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaEvaluationRepository extends JpaRepository<AreaEvaluation, Long> {
    List<AreaEvaluation> findByAreaId(Long areaId);
}
