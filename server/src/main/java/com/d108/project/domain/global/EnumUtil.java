package com.d108.project.domain.global;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class EnumUtil {
    public static <T> T getMostFrequent(List<T> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("The list cannot be null or empty.");
        }

        return items.stream()
                .collect(Collectors.groupingBy(item -> item, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new IllegalArgumentException("Unable to find the most frequent value."))
                .getKey();
    }

    // 제네릭 메서드로 평균 계산 후 Enum 반환
    public static <T extends Scorable> T getAverageValue(List<T> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("The list cannot be null or empty.");
        }

        // 평균 계산
        double averageScore = items.stream()
                .mapToInt(T::getScore)
                .average()
                .orElseThrow(() -> new IllegalArgumentException("Unable to calculate average score."));

        // 평균 점수에 가장 가까운 항목 반환
        return items.stream()
                .min(Comparator.comparingDouble(item -> Math.abs(item.getScore() - averageScore)))
                .orElseThrow(() -> new IllegalArgumentException("Unable to find matching item for average score."));
    }
}
