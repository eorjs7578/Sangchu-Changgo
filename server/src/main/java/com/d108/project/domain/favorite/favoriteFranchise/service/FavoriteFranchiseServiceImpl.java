package com.d108.project.domain.favorite.favoriteFranchise.service;

import com.d108.project.domain.businessArea.dto.BusinessAreaListDto;
import com.d108.project.domain.businessArea.entity.BusinessArea;
import com.d108.project.domain.favorite.favoriteFranchise.dto.FavoriteFranchiseRequestDto;
import com.d108.project.domain.favorite.favoriteFranchise.entity.FavoriteFranchise;
import com.d108.project.domain.favorite.favoriteFranchise.repository.FavoriteFranchiseRepository;
import com.d108.project.domain.franchise.dto.FranchiseListDto;
import com.d108.project.domain.franchise.entity.Franchise;
import com.d108.project.domain.franchise.repository.FranchiseRepository;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteFranchiseServiceImpl implements FavoriteFranchiseService {

    private final FavoriteFranchiseRepository favoriteFranchisesRepository;
    private final FranchiseRepository franchisesRepository;

    private final MemberRepository memberRepository;


    @Override
    public FranchiseListDto getFavoriteFranchisesByMember(Long memberId) {
        List<Franchise> franchises = new ArrayList<>();

        favoriteFranchisesRepository.findAllByMemberId(memberId)
                .forEach(favoriteFranchise -> franchises.add(favoriteFranchise.getFranchise()));

        return FranchiseListDto.createFranchiseListDto(franchises);
    }

    @Override
    public void createFavoriteFranchises(Long memberId, FavoriteFranchiseRequestDto favoriteFranchiseRequestDto) {
        Member member = memberRepository.getReferenceById(memberId);
        Franchise franchises = franchisesRepository.findById(favoriteFranchiseRequestDto.franchiseId()).orElseThrow();

        FavoriteFranchise favorite = FavoriteFranchise.toFavoriteFranchise(member, franchises);
        favoriteFranchisesRepository.save(favorite);
    }

    @Override
    public void deleteFavoriteFranchises(Long memberId, Long id) {
        FavoriteFranchise favoriteFranchises = favoriteFranchisesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("즐겨찾기가 존재하지 않습니다."));

        Member member = favoriteFranchises.getMember();
        if (member.getId().equals(memberId)) {
            throw new IllegalArgumentException("즐겨찾기를 삭제할 수 없습니다.");
        }

        favoriteFranchisesRepository.deleteById(id);
    }


}