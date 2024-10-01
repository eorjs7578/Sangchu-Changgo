package com.d108.project.interfaces.api.favorite;

import com.d108.project.domain.area.dto.AreaListDto;
import com.d108.project.domain.favorite.favoriteArea.dto.FavoriteAreaRequestDto;
import com.d108.project.domain.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/favorite/areas")
public interface FavoriteAreaApi {

    @GetMapping("/list")
    ResponseEntity<AreaListDto> getFavoriteAreasByMember(@AuthenticationPrincipal Member member);

    @GetMapping("/{favoriteAreaId}")
    ResponseEntity<Boolean> checkFavoriteAreaByMember(@AuthenticationPrincipal Member member, @PathVariable Long favoriteAreaId);

    @DeleteMapping("{favoriteAreaId}")
    ResponseEntity<Object> deleteFavoriteArea(@AuthenticationPrincipal Member member, @PathVariable Long favoriteAreaId);

    @PostMapping
    ResponseEntity<Object> createFavoriteArea(@AuthenticationPrincipal Member member, @RequestBody FavoriteAreaRequestDto favoriteAreaRequestDto);

}
