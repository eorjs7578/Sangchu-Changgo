package com.d108.project.domain.global.enums;


import com.d108.project.domain.global.Scorable;
import lombok.Getter;

import java.util.Map;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public enum FootTraffic implements Scorable {
    VERY_HIGH("유동 인구 매우 많음", 5),
    HIGH("유동 인구 많음", 4),
    MEDIUM("유동 인구 보통", 3),
    LOW("유동 인구 적음", 2),
    VERY_LOW("유동 인구 매우 적음", 1);

    private final String description;
    private final int score;

    FootTraffic(String description, int score) {
        this.description = description;
        this.score = score;
    }

    public static List<String> getAllDescriptions() {
        return Arrays.stream(FootTraffic.values())
                .map(FootTraffic::getDescription)
                .toList();
    }
    
    public static FootTraffic fromDescription(String description) {
        return Arrays.stream(FootTraffic.values())
                .filter(atmosphere -> atmosphere.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid description: " + description));
    }


}