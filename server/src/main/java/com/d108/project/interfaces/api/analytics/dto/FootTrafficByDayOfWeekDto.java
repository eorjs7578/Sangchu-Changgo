package com.d108.project.interfaces.api.analytics.dto;

import java.util.List;

public record FootTrafficByDayOfWeekDto(List<Long> data, List<String> labels, String manyPeopleDaysOfWeek) {
}
