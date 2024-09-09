package com.d108.project.domain.favorite.favoriteBusinessArea.repository;

import com.d108.project.domain.favorite.favoriteBusinessArea.entity.FavoriteBusinessArea;
import com.d108.project.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteBusinessAreaRepository extends JpaRepository<FavoriteBusinessArea, Long> {
    List<FavoriteBusinessArea> findAllByMemberId(Long memberId);
}
