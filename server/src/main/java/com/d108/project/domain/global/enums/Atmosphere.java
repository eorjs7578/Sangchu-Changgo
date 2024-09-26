package com.d108.project.domain.global.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Atmosphere {
    LUXURIOUS("고급스러운 분위기"),
    MODERN("모던한 분위기"),
    CASUAL("캐주얼한 분위기"),
    TRADITIONAL("전통적인 분위기");

    private final String description;

    Atmosphere(String description) {
        this.description = description;
    }
    public static List<String> getAllDescriptions() {
        return Arrays.stream(Atmosphere.values())
                .map(Atmosphere::getDescription)
                .toList();
    }
    public static Atmosphere fromDescription(String description) {
        return Arrays.stream(Atmosphere.values())
                .filter(atmosphere -> atmosphere.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid description: " + description));
    }
}
