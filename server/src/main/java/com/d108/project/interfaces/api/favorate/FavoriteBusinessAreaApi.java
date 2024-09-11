package com.d108.project.interfaces.api.favorate;

import com.d108.project.domain.businessArea.dto.BusinessAreaListDto;
import com.d108.project.domain.favorite.favoriteBusinessArea.dto.FavoriteBusinessAreaRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/favorite/business-areas")
public interface FavoriteBusinessAreaApi {

    @GetMapping("/list")
    ResponseEntity<BusinessAreaListDto> getFavoriteBusinessAreasByMember(@RequestParam Long memberId);

    @DeleteMapping("{favoriteBusinessAreaId}")
    ResponseEntity<Object> deleteFavoriteBusinessArea(@RequestParam Long memberId, @PathVariable Long favoriteBusinessAreaId);

    @PostMapping
    ResponseEntity<Object> createFavoriteBusinessArea(@RequestParam Long memberId, @RequestBody FavoriteBusinessAreaRequestDto favoriteBusinessAreaRequestDto);

}
