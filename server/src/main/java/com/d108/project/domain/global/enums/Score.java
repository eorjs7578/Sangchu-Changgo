package com.d108.project.domain.global.enums;

import com.d108.project.domain.global.Scorable;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Getter
public enum Score implements Scorable {
    EXCELLENT("매우 우수",5),
    GOOD("우수",4),
    AVERAGE("보통",3),
    BELOW_AVERAGE("미흡",2),
    POOR("부족",1);

    private final String description;
    private final int score;

    Score(String description, int score) {
        this.description = description;
        this.score = score;
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
