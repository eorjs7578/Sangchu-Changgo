package com.d108.project.domain.evaluation.areaEvaluation.service;

import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaEvaluationDto;
import java.util.List;

public interface AreaEvaluationService {
    AreaEvaluationDto createEvaluation(AreaEvaluationDto dto);
    AreaEvaluationDto updateEvaluation(Long id, AreaEvaluationDto dto);
    void deleteEvaluation(Long id);
    List<AreaEvaluationDto> getEvaluationsByArea(Long areaId);
}
