package com.d108.project.interfaces.controller;

import com.d108.project.domain.favorite.favoriteBusinessArea.service.FavoriteBusinessAreaService;
import com.d108.project.domain.favorite.favoriteFranchise.FavoriteFranchiseRequestDto;
import com.d108.project.domain.favorite.favoriteFranchise.FavoriteFranchiseResponseDto;
import com.d108.project.domain.member.dto.MemberLoginDto;
import com.d108.project.domain.member.dto.MemberRegisterDto;
import com.d108.project.domain.member.dto.MemberResponseDto;
import com.d108.project.domain.member.service.MemberService;
import com.d108.project.interfaces.api.favorate.FavoriteFranchiseApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FavoriteFranchiseController implements FavoriteFranchiseApi {

    private final FavoriteBusinessAreaService favoriteBusinessAreaService;

    @Override
    public ResponseEntity<List<FavoriteFranchiseResponseDto>> getFavoriteFranchisesByMember(Long memberId) {
        return null;
    }

    @Override
    public ResponseEntity<Object> deleteFavoriteFranchise(Long memberId, Long favoriteFranchiseId) {
        return null;
    }

    @Override
    public ResponseEntity<Object> createFavoriteFranchise(Long memberId, FavoriteFranchiseRequestDto favoriteFranchiseRequestDto) {
        return null;
    }
}
