package com.d108.project.domain.global.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum AgeGroup {
    TEENAGER("10대"),
    TWENTIES("20대"),
    THIRTIES("30대"),
    FORTIES("40대"),
    FIFTIES("50대"),
    SIXTIES("60대");

    private final String description;

    AgeGroup(String description) {
        this.description = description;
    }

    public static List<String> getAllDescriptions() {
        return Arrays.stream(AgeGroup.values())
                .map(AgeGroup::getDescription)
                .toList();
    }

    public static AgeGroup fromDescription(String description) {
        return Arrays.stream(AgeGroup.values())
                .filter(atmosphere -> atmosphere.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid description: " + description));
    }


}

