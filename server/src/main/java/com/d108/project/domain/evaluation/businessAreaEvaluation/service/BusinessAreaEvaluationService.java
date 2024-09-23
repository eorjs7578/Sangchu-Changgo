package com.d108.project.domain.evaluation.businessAreaEvaluation.service;

import com.d108.project.domain.evaluation.businessAreaEvaluation.dto.BusinessAreaEvaluationDto;

import java.util.List;

public interface BusinessAreaEvaluationService {
    BusinessAreaEvaluationDto createEvaluation(BusinessAreaEvaluationDto dto);
    BusinessAreaEvaluationDto updateEvaluation(Long id, BusinessAreaEvaluationDto dto);
    void deleteEvaluation(Long id);
    List<BusinessAreaEvaluationDto> getEvaluationsByArea(Long areaId);
}
