package com.d108.project.interfaces.api.favorate;

import com.d108.project.domain.area.dto.AreaListDto;
import com.d108.project.domain.favorite.favoriteArea.dto.FavoriteAreaRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/favorite/areas")
public interface FavoriteAreaApi {

    @GetMapping("/list")
    ResponseEntity<AreaListDto> getFavoriteAreasByMember(@RequestParam Long memberId);

    @DeleteMapping("{favoriteAreaId}")
    ResponseEntity<Object> deleteFavoriteArea(@RequestParam Long memberId, @PathVariable Long favoriteAreaId);

    @PostMapping
    ResponseEntity<Object> createFavoriteArea(@RequestParam Long memberId, @RequestBody FavoriteAreaRequestDto favoriteAreaRequestDto);

}
