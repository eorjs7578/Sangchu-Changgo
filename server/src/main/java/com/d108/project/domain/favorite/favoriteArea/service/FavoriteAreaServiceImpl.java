package com.d108.project.domain.favorite.favoriteArea.service;

import com.d108.project.domain.area.dto.AreaDto;
import com.d108.project.domain.area.dto.AreaListDto;
import com.d108.project.domain.favorite.favoriteArea.dto.FavoriteAreaRequestDto;
import com.d108.project.domain.favorite.favoriteArea.entity.FavoriteArea;
import com.d108.project.domain.favorite.favoriteArea.repository.FavoriteAreaRepository;
import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.area.repository.AreaRepository;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteAreaServiceImpl implements FavoriteAreaService {

    private final FavoriteAreaRepository favoriteAreaRepository;
    private final AreaRepository areaRepository;

    private final MemberRepository memberRepository;

    @Override
    public AreaListDto getFavoriteAreasByMember(Long memberId) {
        System.out.println(memberId);
        List<FavoriteArea> favoriteAreaList = favoriteAreaRepository.findAllByMemberId(memberId);

        if (favoriteAreaList.isEmpty()) {
            return new AreaListDto(null);
        }

        List<FavoriteArea> areas = new ArrayList<>(favoriteAreaList);

        return AreaListDto.createAreaListDto(areas);
    }

    @Override
    public Long checkFavoriteAreaByMember(Long memberId, Long areaId) {
        FavoriteArea favoriteArea = favoriteAreaRepository.findAllByMemberIdAndAreaId(memberId, areaId)
                .orElseThrow(() -> new IllegalArgumentException("관심 상권을 찾을 수 없습니다."));

        return favoriteArea.getId();
    }



    @Override
    public void createFavoriteArea(Long memberId, FavoriteAreaRequestDto favoriteAreaRequestDto) {
        Member member = memberRepository.getReferenceById(memberId);
        Area area = areaRepository.findById(favoriteAreaRequestDto.areaId()).orElseThrow();

        FavoriteArea favorite = FavoriteArea.toFavoriteArea(member, area);
        favoriteAreaRepository.save(favorite);
    }

    @Override
    public void deleteFavoriteArea(Long memberId, Long id) {
        FavoriteArea favoriteArea = favoriteAreaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("즐겨찾기가 존재하지 않습니다."));

        Member member = favoriteArea.getMember();

        if (!member.getId().equals(memberId)) {
            throw new IllegalArgumentException("즐겨찾기를 삭제할 수 없습니다.");
        }

        favoriteAreaRepository.deleteById(id);
    }


}
