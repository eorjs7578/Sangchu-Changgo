package com.d108.project.interfaces.controller.favoriate;

import com.d108.project.domain.businessArea.dto.BusinessAreaListDto;
import com.d108.project.domain.favorite.favoriteBusinessArea.dto.FavoriteBusinessAreaRequestDto;
import com.d108.project.domain.favorite.favoriteBusinessArea.service.FavoriteBusinessAreaService;
import com.d108.project.interfaces.api.favorate.FavoriteBusinessAreaApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "관심 상권")
@RestController
@RequiredArgsConstructor
public class FavoriteBusinessAreaController implements FavoriteBusinessAreaApi {

    private final FavoriteBusinessAreaService favoriteBusinessAreaService;

    @Operation(summary = "[Member] 회원에 대한 모든 관심 상권 조회!", description = "회원과 관련된 모든 관심 상권 조회 (페이징 필요할지도)")
    @Override
    public ResponseEntity<BusinessAreaListDto> getFavoriteBusinessAreasByMember(Long memberId) {
        return ResponseEntity.ok(favoriteBusinessAreaService.getFavoriteBusinessAreasByMember(memberId));
    }

    @Operation(summary = "[Member] 관심 상권 삭제!")
    @Override
    public ResponseEntity<Object> deleteFavoriteBusinessArea(Long memberId, Long favoriteBusinessAreaId) {
        favoriteBusinessAreaService.deleteFavoriteBusinessArea(memberId, favoriteBusinessAreaId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "[Member] 관심 상권 추가!", description = "body 안에 상권 아이디 필요")
    @Override
    public ResponseEntity<Object> createFavoriteBusinessArea(Long memberId, FavoriteBusinessAreaRequestDto favoriteBusinessAreaRequestDto) {
        favoriteBusinessAreaService.createFavoriteBusinessArea(memberId, favoriteBusinessAreaRequestDto);
        return ResponseEntity.ok().build();
    }
}
