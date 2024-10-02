package com.d108.project.domain.area.dto;

import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.favorite.favoriteArea.entity.FavoriteArea;

import java.util.List;

public record AreaListDto(List<AreaDto> areaList) {
    public static AreaListDto createAreaListDto(List<FavoriteArea> favoriteAreas) {
        return new AreaListDto(favoriteAreas.stream().
                map(AreaDto::to)
                .toList()
        );
    }

}
