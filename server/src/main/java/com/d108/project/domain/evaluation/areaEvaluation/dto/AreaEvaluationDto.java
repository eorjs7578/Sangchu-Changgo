package com.d108.project.domain.evaluation.areaEvaluation.dto;

import com.d108.project.domain.global.enums.AgeGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AreaEvaluationDto {
    // Getter 및 Setter
    private Long areaId;
    private Long memberId;
    private String ageGroup;
    private String footTraffic;
    private String atmosphere;
    private String nearbyPrices;
}
