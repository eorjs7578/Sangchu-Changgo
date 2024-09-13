package com.d108.project.domain.businessArea.dto;

import com.d108.project.domain.businessArea.entity.BusinessArea;

import java.util.List;

public record BusinessAreaListDto(List<BusinessAreaDto> businessAreaList) {
    public static BusinessAreaListDto createBusinessAreaListDto(List<BusinessArea> businessAreas) {
        return new BusinessAreaListDto(businessAreas.stream().
                map(BusinessAreaDto::to)
                .toList()
        );
    }

}
