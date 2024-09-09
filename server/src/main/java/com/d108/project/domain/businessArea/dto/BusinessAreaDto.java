package com.d108.project.domain.businessArea.dto;

import com.d108.project.domain.businessArea.entity.BusinessArea;

public record BusinessAreaDto(String name) {


    public static BusinessAreaDto to(BusinessArea businessArea) {
        return new BusinessAreaDto(businessArea.getName());
    }
}
