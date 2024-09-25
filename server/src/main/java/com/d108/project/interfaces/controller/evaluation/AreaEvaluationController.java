package com.d108.project.interfaces.controller.evaluation;

import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaEvaluationDto;
import com.d108.project.domain.evaluation.areaEvaluation.service.AreaEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/area-evaluation")
@RequiredArgsConstructor
public class AreaEvaluationController {

    private final AreaEvaluationService areaEvaluationService;

    // 평가 생성 (POST)
    @PostMapping
    public ResponseEntity<AreaEvaluationDto> createEvaluation(@RequestBody AreaEvaluationDto dto) {
        AreaEvaluationDto createdEvaluation = areaEvaluationService.createEvaluation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvaluation);
    }

    // 평가 수정 (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<AreaEvaluationDto> updateEvaluation(@PathVariable Long id, @RequestBody AreaEvaluationDto dto) {
        AreaEvaluationDto updatedEvaluation = areaEvaluationService.updateEvaluation(id, dto);
        return ResponseEntity.ok(updatedEvaluation);
    }

    // 평가 삭제 (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        areaEvaluationService.deleteEvaluation(id);
        return ResponseEntity.noContent().build();
    }

    // 특정 상권의 평가 조회 (GET)
    @GetMapping("/area/{areaId}")
    public ResponseEntity<List<AreaEvaluationDto>> getEvaluationsByArea(@PathVariable Long areaId) {
        List<AreaEvaluationDto> evaluations = areaEvaluationService.getEvaluationsByArea(areaId);
        return ResponseEntity.ok(evaluations);
    }
}

