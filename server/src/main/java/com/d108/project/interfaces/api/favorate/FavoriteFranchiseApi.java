package com.d108.project.interfaces.api.favorate;

import com.d108.project.domain.favorite.favoriteFranchise.FavoriteFranchiseRequestDto;
import com.d108.project.domain.favorite.favoriteFranchise.FavoriteFranchiseResponseDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/favorite/franchises")
public interface FavoriteFranchiseApi {

    @GetMapping("/list")
    ResponseEntity<List<FavoriteFranchiseResponseDto>> getFavoriteFranchisesByMember(@RequestParam Long memberId);

    @DeleteMapping("{favoriteFranchiseId}")
    ResponseEntity<Object> deleteFavoriteFranchise(@RequestParam Long memberId, @PathVariable Long favoriteFranchiseId);

    @PostMapping
    ResponseEntity<Object> createFavoriteFranchise(@RequestParam Long memberId, @RequestBody FavoriteFranchiseRequestDto favoriteFranchiseRequestDto);
}
