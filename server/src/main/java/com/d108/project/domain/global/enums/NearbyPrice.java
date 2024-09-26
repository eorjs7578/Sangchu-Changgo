package com.d108.project.domain.global.enums;

import com.d108.project.domain.global.Scorable;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Getter
public enum NearbyPrice implements Scorable {
    VERY_EXPENSIVE("매우 비싼 가격대",5),
    EXPENSIVE("비싼 가격대",4),
    AVERAGE("평균적인 가격대",3),
    AFFORDABLE("저렴한 가격대",2),
    BARGAIN("매우 저렴한 가격대",1);

    private final String description;
    private final int score;
    NearbyPrice(String description, int score) {
        this.description = description;
        this.score = score;
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