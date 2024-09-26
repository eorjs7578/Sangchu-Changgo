package com.d108.project.domain.area.analytic.dto;

import java.util.List;

public record FootTrafficByHourDto(List<Long> data, List<String> labels, String manyPeopleDaysOfTheHour) {
}

