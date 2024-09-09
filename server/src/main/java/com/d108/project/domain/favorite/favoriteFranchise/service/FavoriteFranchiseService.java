package com.d108.project.domain.favorite.favoriteFranchise.service;

import com.d108.project.domain.favorite.favoriteFranchise.entity.FavoriteFranchise;
import com.d108.project.domain.member.entity.Member;

import java.util.List;

public interface FavoriteFranchiseService {
    FavoriteFranchise createFavoriteFranchise(Member member, Long franchiseId);
    void deleteFavoriteFranchise(Long id);
    List<FavoriteFranchise> getFavoriteFranchisesByMember(Member member);
}
