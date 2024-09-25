package com.d108.project.interfaces.api.analytics.dto;

import java.util.List;

// 분기별 유동인구
public record FootTrafficByMonthDto(List<Long> data, List<String> labels, String QoQ) {
}
