package com.d108.project.domain.area.dto;

import com.d108.project.domain.area.entity.Area;

import java.util.List;

public record AreaListDto(List<AreaDto> areaList) {
    public static AreaListDto createAreaListDto(List<Area> areas) {
        return new AreaListDto(areas.stream().
                map(AreaDto::to)
                .toList()
        );
    }

}
