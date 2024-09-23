package com.d108.project.domain.evaluation.businessAreaEvaluation.repository;

import com.d108.project.domain.evaluation.businessAreaEvaluation.entity.BusinessAreaEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessAreaEvaluationRepository extends JpaRepository<BusinessAreaEvaluation, Long> {
    List<BusinessAreaEvaluation> findByAreaId(Long areaId);
}

