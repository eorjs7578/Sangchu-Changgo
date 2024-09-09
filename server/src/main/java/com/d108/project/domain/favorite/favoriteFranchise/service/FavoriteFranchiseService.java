package com.d108.project.domain.favorite.favoriteFranchise.service;

import com.d108.project.domain.favorite.favoriteFranchise.dto.FavoriteFranchiseRequestDto;
import com.d108.project.domain.franchise.dto.FranchiseListDto;

import java.util.List;

public interface FavoriteFranchiseService {
    FranchiseListDto getFavoriteFranchisesByMember(Long memberId);
    void createFavoriteFranchises(Long memberId, FavoriteFranchiseRequestDto favoriteFranchiseRequestDto);
    void deleteFavoriteFranchises(Long memberId, Long id);
}
