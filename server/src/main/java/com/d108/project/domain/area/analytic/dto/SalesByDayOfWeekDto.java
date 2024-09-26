package com.d108.project.domain.area.analytic.dto;

import java.util.List;

public record SalesByDayOfWeekDto(List<Long> data, List<String> labels, String manyMoneyDaysOfWeek) {
}
