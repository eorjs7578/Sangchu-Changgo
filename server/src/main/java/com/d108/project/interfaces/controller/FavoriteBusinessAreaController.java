package com.d108.project.interfaces.controller;

import com.d108.project.domain.favorite.favoriteBusinessArea.dto.FavoriteBusinessAreaRequestDto;
import com.d108.project.domain.favorite.favoriteBusinessArea.dto.FavoriteBusinessAreasResponseDto;
import com.d108.project.interfaces.api.favorate.FavoriteBusinessAreaApi;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class FavoriteBusinessAreaController implements FavoriteBusinessAreaApi {
    @Override
    public ResponseEntity<List<FavoriteBusinessAreasResponseDto>> getFavoriteBusinessAreasByMember(Long memberId) {
        return null;
    }

    @Override
    public ResponseEntity<Object> deleteFavoriteBusinessArea(Long memberId, Long favoriteBusinessAreaId) {
        return null;
    }

    @Override
    public ResponseEntity<Object> createFavoriteBusinessArea(Long memberId, FavoriteBusinessAreaRequestDto favoriteBusinessAreaRequestDto) {
        return null;
    }
}
