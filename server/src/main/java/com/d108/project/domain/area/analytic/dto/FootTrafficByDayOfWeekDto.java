package com.d108.project.domain.area.analytic.dto;

import java.util.List;

public record FootTrafficByDayOfWeekDto(List<Long> data, List<String> labels, String manyPeopleDaysOfWeek) {
}
