package com.d108.project.interfaces.api.evaluation;

import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaEvaluationCreateDto;
import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaEvaluationDto;
import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaEvaluationUpdateDto;
import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaTypeListDto;
import com.d108.project.domain.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/area-evaluation")
public interface AreaEvaluationAPI {

    @PostMapping("/{areaId}")
    ResponseEntity<AreaEvaluationDto> createEvaluation(@AuthenticationPrincipal Member member, @PathVariable Long areaId,@RequestBody AreaEvaluationCreateDto dto);

    @PutMapping("/{evaluationId}")
    ResponseEntity<AreaEvaluationDto> updateEvaluation(@AuthenticationPrincipal Member member, @PathVariable Long evaluationId, @RequestBody AreaEvaluationUpdateDto dto);

    @DeleteMapping("/{evaluationId}")
    ResponseEntity<Void> deleteEvaluation(@AuthenticationPrincipal Member member, @PathVariable Long evaluationId);

    @GetMapping("/statistics/{areaId}")
    ResponseEntity<AreaEvaluationDto> getAreaEvaluationStatistics(@PathVariable Long areaId);

    @GetMapping("/type")
    ResponseEntity<AreaTypeListDto> getEvaluationsByAreaTypeList();

    @GetMapping("/{areaId}")
    ResponseEntity<List<AreaEvaluationDto>> getEvaluationsByArea(@AuthenticationPrincipal Member member, @PathVariable Long areaId);

}

