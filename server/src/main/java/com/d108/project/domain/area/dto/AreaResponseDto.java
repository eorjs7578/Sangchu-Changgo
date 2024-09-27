package com.d108.project.domain.area.dto;

import com.d108.project.domain.area.entity.Area;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AreaResponseDto {
    private String areaName;

    public static AreaResponseDto from(Area area) {
        return AreaResponseDto.builder()
                .areaName(area.getAreaName())
                .build();
    }
}
