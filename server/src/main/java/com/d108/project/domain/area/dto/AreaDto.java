package com.d108.project.domain.area.dto;

import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.favorite.favoriteArea.entity.FavoriteArea;

public record AreaDto(String name, Long AreaId, Long favoriteId) {


    public static AreaDto to(FavoriteArea area) {
        return new AreaDto(area.getArea().getAreaName(), area.getArea().getId(), area.getId());
    }
}
