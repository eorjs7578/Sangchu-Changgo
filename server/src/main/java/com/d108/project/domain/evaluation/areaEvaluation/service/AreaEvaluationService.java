package com.d108.project.domain.evaluation.areaEvaluation.service;

import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaEvaluationCreateDto;
import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaEvaluationDto;
import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaEvaluationUpdateDto;
import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaTypeListDto;
import com.d108.project.domain.member.entity.Member;

import java.util.List;

public interface AreaEvaluationService {
    AreaEvaluationDto createEvaluation(Member member, Long areaId, AreaEvaluationCreateDto dto);
    AreaEvaluationDto updateEvaluation(Member member,Long evaluation, AreaEvaluationUpdateDto dto);
    void deleteEvaluation(Member member, Long evaluation);
    AreaEvaluationDto getEvaluationsByAreaAndMember(Member member, Long areaId);
    AreaTypeListDto getEvaluationsByAreaTypeList();
    AreaEvaluationDto getAreaEvaluationStatistics(Long areaId);
}
