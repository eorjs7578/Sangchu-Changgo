package com.d108.project.domain.favorite.favoriteArea.service;

import com.d108.project.domain.area.dto.AreaDto;
import com.d108.project.domain.area.dto.AreaListDto;
import com.d108.project.domain.favorite.favoriteArea.dto.FavoriteAreaRequestDto;
import com.d108.project.domain.favorite.favoriteArea.dto.FavoriteAreaResponse;

import java.util.List;

public interface FavoriteAreaService {
    AreaListDto getFavoriteAreasByMember(Long memberId);
    FavoriteAreaResponse getFavoriteAreaIdByMember(Long memberId, Long areaId);
    Long createFavoriteArea(Long memberId, FavoriteAreaRequestDto favoriteAreaRequestDto);
    void deleteFavoriteArea(Long memberId, Long id);
}
