package com.d108.project.interfaces.api.evaluation;

import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaEvaluationDto;
import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaTypeListDto;
import com.d108.project.domain.evaluation.areaEvaluation.service.AreaEvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/area-evaluation")
public interface AreaEvaluationAPI {

    @GetMapping("/type")
    ResponseEntity<AreaTypeListDto> getEvaluationsByAreaTypeList();
}

