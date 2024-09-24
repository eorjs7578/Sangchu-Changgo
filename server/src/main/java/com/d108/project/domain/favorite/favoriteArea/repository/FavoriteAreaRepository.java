package com.d108.project.domain.favorite.favoriteArea.repository;

import com.d108.project.domain.favorite.favoriteArea.entity.FavoriteArea;
import com.d108.project.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteAreaRepository extends JpaRepository<FavoriteArea, Long> {
    List<FavoriteArea> findAllByMemberId(Long memberId);
}
