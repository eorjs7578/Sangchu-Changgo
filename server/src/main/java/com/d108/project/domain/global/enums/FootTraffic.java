package com.d108.project.domain.global.enums;


import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum FootTraffic {
    VERY_HIGH("유동 인구 매우 많음"),
    HIGH("유동 인구 많음"),
    MEDIUM("유동 인구 보통"),
    LOW("유동 인구 적음"),
    VERY_LOW("유동 인구 매우 적음");

    private final String description;

    FootTraffic(String description) {
        this.description = description;
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