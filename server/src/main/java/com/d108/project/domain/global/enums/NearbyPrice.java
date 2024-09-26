package com.d108.project.domain.global.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum NearbyPrice {
    VERY_EXPENSIVE("매우 비싼 가격대"),
    EXPENSIVE("비싼 가격대"),
    AVERAGE("평균적인 가격대"),
    AFFORDABLE("저렴한 가격대"),
    BARGAIN("매우 저렴한 가격대");

    private final String description;

    NearbyPrice(String description) {
        this.description = description;
    }

    public static List<String> getAllDescriptions() {
        return Arrays.stream(NearbyPrice.values())
                .map(NearbyPrice::getDescription)
                .toList();
    }
    
    public static NearbyPrice fromDescription(String description) {
        return Arrays.stream(NearbyPrice.values())
                .filter(atmosphere -> atmosphere.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid description: " + description));
    }
}