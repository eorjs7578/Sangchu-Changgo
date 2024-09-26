package com.d108.project.interfaces.controller.evaluation;

import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaEvaluationDto;
import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaTypeListDto;
import com.d108.project.domain.evaluation.areaEvaluation.service.AreaEvaluationService;
import com.d108.project.interfaces.api.evaluation.AreaEvaluationAPI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="상권 평가")
@RestController
@RequestMapping("/api/area-evaluation")
@RequiredArgsConstructor
public class AreaEvaluationController implements AreaEvaluationAPI {

    private final AreaEvaluationService areaEvaluationService;

    // 평가 생성 (POST)
    @Operation(summary = "[Member] 상권의 평가 생성 ", description = "")
    @PostMapping
    public ResponseEntity<AreaEvaluationDto> createEvaluation(@RequestBody AreaEvaluationDto dto) {
        AreaEvaluationDto createdEvaluation = areaEvaluationService.createEvaluation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvaluation);
    }

    // 평가 수정 (PUT)
    @Operation(summary = "[Member] 상권의 평가 수정 ", description = "")
    @PutMapping("/{id}")
    public ResponseEntity<AreaEvaluationDto> updateEvaluation(@PathVariable Long id, @RequestBody AreaEvaluationDto dto) {
        AreaEvaluationDto updatedEvaluation = areaEvaluationService.updateEvaluation(id, dto);
        return ResponseEntity.ok(updatedEvaluation);
    }

    // 평가 삭제 (DELETE)
    @Operation(summary = "[Member] 상권의 평가 삭제 ", description = "")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        areaEvaluationService.deleteEvaluation(id);
        return ResponseEntity.noContent().build();
    }

    // 특정 상권의 평가 조회 (GET)
    @Operation(summary = "[ALL] 특정 상권의 평가 조회", description = "")
    @GetMapping("/area/{areaId}")
    public ResponseEntity<List<AreaEvaluationDto>> getEvaluationsByArea(@PathVariable Long areaId) {
        List<AreaEvaluationDto> evaluations = areaEvaluationService.getEvaluationsByArea(areaId);
        return ResponseEntity.ok(evaluations);
    }


    @Operation(summary = "[ALL] 상권 타입 리스트 조회", description = "분위기, 유동인구 연령대, 유동인구 복잡 정도, 주변 물가 반환 ")
    @Override
    public ResponseEntity<AreaTypeListDto> getEvaluationsByAreaTypeList() {
        AreaTypeListDto evaluationTypes = areaEvaluationService.getEvaluationsByAreaTypeList();
        return ResponseEntity.ok(evaluationTypes);
    }
}

