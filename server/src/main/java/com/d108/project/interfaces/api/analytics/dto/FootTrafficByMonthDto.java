package com.d108.project.interfaces.api.analytics.dto;

import java.util.List;

// 분기별 유동인구
public class FootTrafficByMonthDto {
    private List<Long> data;
    private List<String> labels;
    // 이전 분기 대비 (감소 or 증가)
    private String QoQ;
}
