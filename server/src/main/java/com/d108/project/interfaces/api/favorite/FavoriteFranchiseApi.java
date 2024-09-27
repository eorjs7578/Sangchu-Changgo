package com.d108.project.interfaces.api.favorite;

import com.d108.project.domain.favorite.favoriteFranchise.dto.FavoriteFranchiseRequestDto;
import com.d108.project.domain.favorite.favoriteFranchise.dto.FavoriteFranchiseResponseDto;
import com.d108.project.domain.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/favorite/franchises")
public interface FavoriteFranchiseApi {

    @GetMapping("/list")
    ResponseEntity<List<FavoriteFranchiseResponseDto>> getFavoriteFranchisesByMember(@AuthenticationPrincipal Member member);

    @DeleteMapping("/{favoriteFranchiseId}")
    ResponseEntity<Object> deleteFavoriteFranchise(@AuthenticationPrincipal Member member, @PathVariable Long favoriteFranchiseId);

    @PostMapping
    ResponseEntity<Object> createFavoriteFranchise(@AuthenticationPrincipal Member member, @RequestBody FavoriteFranchiseRequestDto favoriteFranchiseDto);
}