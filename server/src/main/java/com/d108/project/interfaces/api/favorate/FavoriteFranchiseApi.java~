package com.d108.project.interfaces.api.favorate;

import com.d108.project.domain.favorite.favoriteFranchise.dto.FavoriteFranchiseRequestDto;
import com.d108.project.domain.franchise.dto.FranchiseListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/favorite/franchises")
public interface FavoriteFranchiseApi {

    @GetMapping("/list")
    ResponseEntity<FranchiseListDto> getFavoriteFranchisesByMember(@RequestParam Long memberId);

    @DeleteMapping("{favoriteFranchiseId}")
    ResponseEntity<Object> deleteFavoriteFranchise(@RequestParam Long memberId, @PathVariable Long favoriteFranchiseId);

    @PostMapping
    ResponseEntity<Object> createFavoriteFranchise(@RequestParam Long memberId, @RequestBody FavoriteFranchiseRequestDto favoriteFranchiseRequestDto);
}
