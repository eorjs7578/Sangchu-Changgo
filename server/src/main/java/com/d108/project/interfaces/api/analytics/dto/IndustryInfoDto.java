package com.d108.project.interfaces.api.analytics.dto;

import java.util.List;

public class IndustryInfoDto {
    private List<Long> data;
    private List<String> labels;
}

// 상권에 업종이 여러개 있어.
//  그 상권에 그 업종에 대한 매출정보

// 업종별 매출정보