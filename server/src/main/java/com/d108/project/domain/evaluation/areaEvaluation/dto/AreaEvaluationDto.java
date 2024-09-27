package com.d108.project.domain.evaluation.areaEvaluation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AreaEvaluationDto {
    // Getter 및 Setter
    private Long evaluationId;
    private Long areaId;
    private String areaName;
    private String ageGroup;
    private String footTraffic;
    private String atmosphere;
    private String nearbyPrices;

}
