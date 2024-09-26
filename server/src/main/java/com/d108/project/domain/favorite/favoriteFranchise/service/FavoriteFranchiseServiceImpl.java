package com.d108.project.domain.favorite.favoriteFranchise.service;

import com.d108.project.domain.area.analytic.entity.Rent;
import com.d108.project.domain.area.analytic.repository.RentRepository;
import com.d108.project.domain.favorite.favoriteFranchise.dto.FavoriteFranchiseRequestDto;
import com.d108.project.domain.favorite.favoriteFranchise.dto.FavoriteFranchiseResponseDto;
import com.d108.project.domain.favorite.favoriteFranchise.entity.FavoriteFranchise;
import com.d108.project.domain.favorite.favoriteFranchise.repository.FavoriteFranchiseRepository;
import com.d108.project.domain.franchise.dto.FranchiseFeeDto;
import com.d108.project.domain.franchise.entity.Franchise;
import com.d108.project.domain.franchise.repository.FranchiseRepository;
import com.d108.project.domain.map.dto.RegionInfoDto;
import com.d108.project.domain.map.entity.Dong;
import com.d108.project.domain.map.entity.Gu;
import com.d108.project.domain.map.respository.DongRepository;
import com.d108.project.domain.map.respository.GuRepository;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteFranchiseServiceImpl implements FavoriteFranchiseService {

    private final FavoriteFranchiseRepository favoriteFranchisesRepository;
    private final FranchiseRepository franchisesRepository;
    private final MemberRepository memberRepository;
    private final DongRepository dongRepository;
    private final GuRepository guRepository;
    private final RentRepository rentRepository;


    @Override
    public List<FavoriteFranchiseResponseDto> getFavoriteFranchisesByMember(Long memberId) {
        List<FavoriteFranchiseResponseDto> franchises = new ArrayList<>();
        List<FavoriteFranchise> favoriteFranchises = favoriteFranchisesRepository.findAllByMemberId(memberId);
        for (FavoriteFranchise favoriteFranchise : favoriteFranchises) {
            Franchise franchise = favoriteFranchise.getFranchise();
            Rent rent = rentRepository.findByDongId(favoriteFranchise.getDong().getId());
            Long rentFee = favoriteFranchise.getFloor() ? rent.getFirstFloorRent() : rent.getOtherFloorRent();
            RegionInfoDto gu = RegionInfoDto.from(favoriteFranchise.getGu());
            RegionInfoDto dong = RegionInfoDto.from(favoriteFranchise.getDong());
            franchises.add(FavoriteFranchiseResponseDto.
                    from(FranchiseFeeDto.to(franchise,rentFee,favoriteFranchise.getId()),gu,dong
                            ,favoriteFranchise.getSize(),favoriteFranchise.getFloor()));
        }


        return franchises;
    }

    @Override
    public Long createFavoriteFranchises(Long memberId, FavoriteFranchiseRequestDto favoriteFranchiseRequestDto) {
        Member member = memberRepository.getReferenceById(memberId);
        Dong dong = dongRepository.findById(favoriteFranchiseRequestDto.dongCode())
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 동 입니다."));
        boolean isLikedFranchise = favoriteFranchisesRepository
                .findByMemberIdAndFranchiseIdAndDongAndSizeAndFloor(member.getId(),
                        favoriteFranchiseRequestDto.franchiseId(),dong,favoriteFranchiseRequestDto.storeSize(),
                        favoriteFranchiseRequestDto.floor())
                .isPresent();
        if(isLikedFranchise) {
            throw new IllegalArgumentException("이미 좋아요 한 프랜차이즈입니다.");
        }
        Franchise franchise = franchisesRepository.findById(favoriteFranchiseRequestDto.franchiseId())
                .orElseThrow(() -> new IllegalArgumentException("프랜차이즈가 존재하지 않습니다."));
        Gu gu = guRepository.findById(dong.getCode().getId())
                .orElseThrow(() -> new IllegalArgumentException("구코드가 존재하지 않습니다."));
        FavoriteFranchise favorite = FavoriteFranchise.toFavoriteFranchise(member,favoriteFranchiseRequestDto,franchise,dong,gu);
        return favoriteFranchisesRepository.save(favorite).getId();
    }

    @Override
    @Transactional
    public void deleteFavoriteFranchises(Long memberId, Long franchiseId) {
        FavoriteFranchise favoriteFranchises = favoriteFranchisesRepository.findById(franchiseId)
                .orElseThrow(() -> new IllegalArgumentException("즐겨찾기가 존재하지 않습니다."));
        Member member = favoriteFranchises.getMember();

        if (!member.getId().equals(memberId)) {
            throw new IllegalArgumentException("즐겨찾기를 삭제할 수 없습니다.");
        }

        favoriteFranchisesRepository.deleteById(franchiseId);
    }


}
