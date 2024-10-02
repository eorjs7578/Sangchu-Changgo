package com.d108.project.domain.area.dto;

import com.d108.project.domain.area.entity.Area;

public record AreaDto(String name, Long AreaId) {


    public static AreaDto to(Area area) {
        return new AreaDto(area.getAreaName(), area.getId());
    }
}
