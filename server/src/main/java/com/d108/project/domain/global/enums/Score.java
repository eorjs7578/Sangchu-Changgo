package com.d108.project.domain.global.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Score {
    EXCELLENT("매우 우수"),
    GOOD("우수"),
    AVERAGE("보통"),
    BELOW_AVERAGE("미흡"),
    POOR("부족");

    private final String description;

    Score(String description) {
        this.description = description;
    }

    public static List<String> getAllDescriptions() {
        return Arrays.stream(Score.values())
                .map(Score::getDescription)
                .toList();
    }
    
    public static Score fromDescription(String description) {
        return Arrays.stream(Score.values())
                .filter(atmosphere -> atmosphere.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid description: " + description));
    }

}
