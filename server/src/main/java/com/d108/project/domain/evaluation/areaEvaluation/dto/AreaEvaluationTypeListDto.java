package com.d108.project.domain.evaluation.areaEvaluation.dto;

import java.util.List;

public record AreaEvaluationTypeListDto(List<String> atmospheres,
                                        List<String> ageGroups,
                                        List<String> footTraffics,
                                        List<String> nearbyPrices) {
}
