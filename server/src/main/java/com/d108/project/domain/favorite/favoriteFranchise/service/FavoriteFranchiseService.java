package com.d108.project.domain.favorite.favoriteFranchise.service;

import com.d108.project.domain.favorite.favoriteFranchise.dto.FavoriteFranchiseRequestDto;
import com.d108.project.domain.favorite.favoriteFranchise.dto.FavoriteFranchiseResponseDto;

import java.util.List;

public interface FavoriteFranchiseService {
    List<FavoriteFranchiseResponseDto> getFavoriteFranchisesByMember(Long memberId);
    Long createFavoriteFranchises(Long memberId, FavoriteFranchiseRequestDto favoriteFranchiseDto);
    void deleteFavoriteFranchises(Long memberId, Long franchiseId);
}
