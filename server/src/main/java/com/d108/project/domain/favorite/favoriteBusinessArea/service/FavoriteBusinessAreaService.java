package com.d108.project.domain.favorite.favoriteBusinessArea.service;

import com.d108.project.domain.favorite.favoriteBusinessArea.entity.FavoriteBusinessArea;
import com.d108.project.domain.member.entity.Member;

import java.util.List;

public interface FavoriteBusinessAreaService {
    FavoriteBusinessArea createFavoriteBusinessArea(Member member, Long businessAreaId);
    void deleteFavoriteBusinessArea(Long id);
    List<Long> getFavoriteBusinessAreasByMember(Member member);
}
