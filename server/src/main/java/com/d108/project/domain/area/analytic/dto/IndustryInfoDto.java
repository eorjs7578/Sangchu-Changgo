package com.d108.project.domain.area.analytic.dto;

import java.util.List;

public record IndustryInfoDto(List<Integer> industryInfo, List<String> info) {

    public IndustryInfoDto(List<Integer> industryInfo, List<String> info) {
        this.industryInfo = industryInfo;
        this.info = info;
    }
}

// 상권에 업종이 여러개 있어.
//  그 상권에 그 업종에 대한 매출정보

// 업종별 매출정보