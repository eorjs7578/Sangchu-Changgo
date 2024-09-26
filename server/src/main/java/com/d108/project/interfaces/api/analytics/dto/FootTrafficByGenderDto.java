package com.d108.project.interfaces.api.analytics.dto;

import java.util.List;

public record FootTrafficByGenderDto(List<Long> data, List<String> labels, String manyPeopleGender) {
}
