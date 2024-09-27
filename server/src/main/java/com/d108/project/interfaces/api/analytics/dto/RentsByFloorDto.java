package com.d108.project.interfaces.api.analytics.dto;

import java.util.List;

public record RentsByFloorDto(List<Long> data, List<String> labels) {
}
