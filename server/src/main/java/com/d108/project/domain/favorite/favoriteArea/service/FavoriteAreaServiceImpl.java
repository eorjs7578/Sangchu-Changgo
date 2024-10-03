package com.d108.project.domain.favorite.favoriteArea.service;

import com.d108.project.domain.area.dto.AreaDto;
import com.d108.project.domain.area.dto.AreaListDto;
import com.d108.project.domain.favorite.favoriteArea.dto.FavoriteAreaRequestDto;
import com.d108.project.domain.favorite.favoriteArea.dto.FavoriteAreaResponse;
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
    public FavoriteAreaResponse getFavoriteAreaIdByMember(Long memberId, Long areaId) {
        Optional<FavoriteArea> favoriteArea = favoriteAreaRepository.findAllByMemberIdAndAreaId(memberId, areaId);

        return favoriteArea.map(area -> new FavoriteAreaResponse(true, area.getId()))
                .orElseGet(() -> new FavoriteAreaResponse(false, 0L));
    }



    @Override
    public Long createFavoriteArea(Long memberId, FavoriteAreaRequestDto favoriteAreaRequestDto) {
        Member member = memberRepository.getReferenceById(memberId);
        Area area = areaRepository.findById(favoriteAreaRequestDto.areaId())
                .orElseThrow(() -> new IllegalArgumentException("area ID 값이 올바르지 않습니다."));

        Optional<FavoriteArea> favoriteArea = favoriteAreaRepository.findAllByMemberIdAndAreaId(memberId, area.getId());
        if (favoriteArea.isPresent()) throw new IllegalArgumentException("이미 해당 관심상권이 있습니다.");

        FavoriteArea favorite = FavoriteArea.toFavoriteArea(member, area);
        FavoriteArea save = favoriteAreaRepository.save(favorite);
        System.out.println(save);
        favoriteArea = favoriteAreaRepository.findAllByMemberIdAndAreaId(memberId, area.getId());
        System.out.println(favoriteArea.get().getId());
        return favoriteArea.get().getId();
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
