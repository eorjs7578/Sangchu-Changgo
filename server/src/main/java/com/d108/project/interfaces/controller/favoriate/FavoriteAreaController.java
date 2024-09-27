package com.d108.project.interfaces.controller.favoriate;

import com.d108.project.domain.area.dto.AreaListDto;
import com.d108.project.domain.favorite.favoriteArea.dto.FavoriteAreaRequestDto;
import com.d108.project.domain.favorite.favoriteArea.service.FavoriteAreaService;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.interfaces.api.favorate.FavoriteAreaApi;
import com.d108.project.interfaces.api.favorite.FavoriteAreaApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "관심 상권")
@RestController
@RequiredArgsConstructor
public class FavoriteAreaController implements FavoriteAreaApi {

    private final FavoriteAreaService favoriteAreaService;

    @Operation(summary = "[Member] 회원에 대한 모든 관심 상권 조회!", description = "회원과 관련된 모든 관심 상권 조회 (페이징 필요할지도)")
    @Override
    public ResponseEntity<AreaListDto> getFavoriteAreasByMember(Member member) {
        return ResponseEntity.ok(favoriteAreaService.getFavoriteAreasByMember(member.getId()));
    }

    @Operation(summary = "[Member] 관심 상권 삭제!")
    @Override
    public ResponseEntity<Object> deleteFavoriteArea(Member member, Long favoriteAreaId) {
        favoriteAreaService.deleteFavoriteArea(member.getId(), favoriteAreaId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "[Member] 관심 상권 추가!", description = "body 안에 상권 아이디 필요")
    @Override
    public ResponseEntity<Object> createFavoriteArea(Member member, FavoriteAreaRequestDto favoriteAreaRequestDto) {
        favoriteAreaService.createFavoriteArea(member.getId(), favoriteAreaRequestDto);
        return ResponseEntity.ok().build();
    }
}
