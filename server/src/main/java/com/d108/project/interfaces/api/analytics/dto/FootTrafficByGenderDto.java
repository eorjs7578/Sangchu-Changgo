package com.d108.project.interfaces.api.analytics.dto;

import java.util.List;

public class FootTrafficByGenderDto {
    private List<Long> data;
    // 남성, 여성
    private List<String> labels;
    private String manyPeopleGender;
}
