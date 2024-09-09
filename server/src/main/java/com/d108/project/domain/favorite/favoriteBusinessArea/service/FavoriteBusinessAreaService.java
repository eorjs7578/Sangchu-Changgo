package com.d108.project.domain.favorite.favoriteBusinessArea.service;

import com.d108.project.domain.businessArea.dto.BusinessAreaListDto;
import com.d108.project.domain.favorite.favoriteBusinessArea.dto.FavoriteBusinessAreaRequestDto;

import java.util.List;

public interface FavoriteBusinessAreaService {
    BusinessAreaListDto getFavoriteBusinessAreasByMember(Long memberId);
    void createFavoriteBusinessArea(Long memberId, FavoriteBusinessAreaRequestDto favoriteBusinessAreaRequestDto);
    void deleteFavoriteBusinessArea(Long memberId, Long id);
}
