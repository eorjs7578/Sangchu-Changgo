package com.d108.project.interfaces.api.favorite;

import com.d108.project.domain.franchise.dto.FranchiseListDto;
import com.d108.project.domain.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/favorite/franchises")
public interface FavoriteFranchiseApi {

    @GetMapping("/list")
    ResponseEntity<FranchiseListDto> getFavoriteFranchisesByMember(@AuthenticationPrincipal Member member);

    @DeleteMapping("/{favoriteFranchiseId}")
    ResponseEntity<Object> deleteFavoriteFranchise(@AuthenticationPrincipal Member member, @PathVariable Long favoriteFranchiseId);

    @PostMapping("/{favoriteFranchiseId}")
    ResponseEntity<Object> createFavoriteFranchise(@AuthenticationPrincipal Member member, @PathVariable Long favoriteFranchiseId);
}