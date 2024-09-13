package com.d108.project.domain.favorite.favoriteFranchise.repository;

import com.d108.project.domain.favorite.favoriteFranchise.entity.FavoriteFranchise;
import com.d108.project.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteFranchiseRepository extends JpaRepository<FavoriteFranchise, Long> {
    List<FavoriteFranchise> findAllByMemberId(Long memberId);
}
