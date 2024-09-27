package com.d108.project.domain.favorite.favoriteFranchise.repository;

import com.d108.project.domain.favorite.favoriteFranchise.entity.FavoriteFranchise;
import com.d108.project.domain.map.entity.Dong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteFranchiseRepository extends JpaRepository<FavoriteFranchise, Long> {
    List<FavoriteFranchise> findAllByMemberId(Long memberId);
    Optional<FavoriteFranchise> findByMemberIdAndFranchiseIdAndDongAndSizeAndFloor(Long memberId, Long franchiseId, Dong dong, Long size, Boolean floor);
}
