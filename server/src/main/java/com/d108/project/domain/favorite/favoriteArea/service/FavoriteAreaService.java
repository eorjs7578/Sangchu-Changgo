package com.d108.project.domain.favorite.favoriteArea.service;

import com.d108.project.domain.area.dto.AreaListDto;
import com.d108.project.domain.favorite.favoriteArea.dto.FavoriteAreaRequestDto;

import java.util.List;

public interface FavoriteAreaService {
    AreaListDto getFavoriteAreasByMember(Long memberId);
    void createFavoriteArea(Long memberId, FavoriteAreaRequestDto favoriteAreaRequestDto);
    void deleteFavoriteArea(Long memberId, Long id);
}
