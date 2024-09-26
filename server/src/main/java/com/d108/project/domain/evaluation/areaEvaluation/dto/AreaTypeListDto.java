package com.d108.project.domain.evaluation.areaEvaluation.dto;

import java.util.List;

public record AreaTypeListDto(List<String> atmospheres,
                              List<String> footTraffics,
                              List<String> nearbyPrices,
                              List<String> ageGroups) {
}
