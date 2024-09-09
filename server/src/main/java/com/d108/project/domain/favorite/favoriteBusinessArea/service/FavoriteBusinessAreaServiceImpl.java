package com.d108.project.domain.favorite.favoriteBusinessArea.service;

import com.d108.project.domain.businessArea.dto.BusinessAreaListDto;
import com.d108.project.domain.favorite.favoriteBusinessArea.dto.FavoriteBusinessAreaRequestDto;
import com.d108.project.domain.favorite.favoriteBusinessArea.entity.FavoriteBusinessArea;
import com.d108.project.domain.favorite.favoriteBusinessArea.repository.FavoriteBusinessAreaRepository;
import com.d108.project.domain.businessArea.entity.BusinessArea;
import com.d108.project.domain.businessArea.repository.BusinessAreaRepository;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteBusinessAreaServiceImpl implements FavoriteBusinessAreaService {

    private final FavoriteBusinessAreaRepository favoriteBusinessAreaRepository;
    private final BusinessAreaRepository businessAreaRepository;

    private final MemberRepository memberRepository;

    @Override
    public BusinessAreaListDto getFavoriteBusinessAreasByMember(Long memberId) {
        List<BusinessArea> businessAreas = new ArrayList<>();

        favoriteBusinessAreaRepository.findAllByMemberId(memberId)
                .forEach(favoriteBusinessArea -> businessAreas.add(favoriteBusinessArea.getBusinessArea()));

        return BusinessAreaListDto.createBusinessAreaListDto(businessAreas);
    }

    @Override
    public void createFavoriteBusinessArea(Long memberId, FavoriteBusinessAreaRequestDto favoriteBusinessAreaRequestDto) {
        Member member = memberRepository.getReferenceById(memberId);
        BusinessArea businessArea = businessAreaRepository.findById(favoriteBusinessAreaRequestDto.businessAreaId()).orElseThrow();

        FavoriteBusinessArea favorite = FavoriteBusinessArea.toFavoriteBusinessArea(member, businessArea);
        favoriteBusinessAreaRepository.save(favorite);
    }

    @Override
    public void deleteFavoriteBusinessArea(Long memberId, Long id) {
        FavoriteBusinessArea favoriteBusinessArea = favoriteBusinessAreaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("즐겨찾기가 존재하지 않습니다."));

        Member member = favoriteBusinessArea.getMember();

        if (!member.getId().equals(memberId)) {
            throw new IllegalArgumentException("즐겨찾기를 삭제할 수 없습니다.");
        }

        favoriteBusinessAreaRepository.deleteById(id);
    }


}
