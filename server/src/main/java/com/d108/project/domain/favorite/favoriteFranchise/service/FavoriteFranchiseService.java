package com.d108.project.domain.favorite.favoriteFranchise.service;

import com.d108.project.domain.franchise.dto.FranchiseListDto;

public interface FavoriteFranchiseService {
    FranchiseListDto getFavoriteFranchisesByMember(Long memberId);
    void createFavoriteFranchises(Long memberId, Long franchiseId);
    void deleteFavoriteFranchises(Long memberId, Long franchiseId);
}
