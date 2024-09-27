package com.d108.project.domain.franchise.service;

import com.d108.project.domain.area.analytic.entity.Rent;
import com.d108.project.domain.area.analytic.repository.RentRepository;
import com.d108.project.domain.favorite.favoriteFranchise.entity.FavoriteFranchise;
import com.d108.project.domain.favorite.favoriteFranchise.repository.FavoriteFranchiseRepository;
import com.d108.project.domain.franchise.dto.FranchiseDto;
import com.d108.project.domain.franchise.dto.FranchiseFeeDto;
import com.d108.project.domain.franchise.dto.FranchiseTypeDto;
import com.d108.project.domain.franchise.entity.Franchise;
import com.d108.project.domain.franchise.repository.FranchiseRepository;
import com.d108.project.domain.map.entity.Dong;
import com.d108.project.domain.map.respository.DongRepository;
import com.d108.project.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FranchiseServiceImpl implements FranchiseService {

    private final FranchiseRepository franchiseRepository;
    private final RentRepository rentRepository;
    private final FavoriteFranchiseRepository favoriteFranchisesRepository;
    private final DongRepository dongRepository;
    @Override
    public List<FranchiseTypeDto> getFranchiseTypes() {
        List<String> types = franchiseRepository.findDistinctByType();
        List<FranchiseTypeDto> franchiseTypes = new ArrayList<>();
        for (String type : types) {
            if(type == null) {
                continue;
            }
            FranchiseTypeDto franchiseTypeDto = FranchiseTypeDto.to(type);
            franchiseTypes.add(franchiseTypeDto);
        }
        return franchiseTypes;
    }

    @Override
    public List<FranchiseDto> getFranchisesByType(String type) {
        List<Franchise> franchises = franchiseRepository.findAllByType(type);
        List<FranchiseDto> franchiseDtos = new ArrayList<>();
        for (Franchise franchise : franchises) {
            FranchiseDto dto = FranchiseDto.to(franchise);
            franchiseDtos.add(dto);
        }
        return franchiseDtos;
    }

    @Override
    public List<FranchiseDto> getFranchisesByName(String name) {
        List<Franchise> franchises = franchiseRepository.findAllByName(name);
        List<FranchiseDto> franchiseDtos = new ArrayList<>();
        for (Franchise franchise : franchises) {
            FranchiseDto dto = FranchiseDto.to(franchise);
            franchiseDtos.add(dto);
        }
        return franchiseDtos;
    }

    @Override
    public List<FranchiseDto> getAllFranchises() {
        List<Franchise> franchises = franchiseRepository.findAll();
        List<FranchiseDto> franchiseDtos = new ArrayList<>();
        for (Franchise franchise : franchises) {
            FranchiseDto dto = FranchiseDto.to(franchise);
            franchiseDtos.add(dto);
        }
        return franchiseDtos;
    }

    @Override
    public FranchiseFeeDto getFranchiseFee(Member member, Long id, Long dongCode, Long size, Boolean floor) {
        Franchise franchise = franchiseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 프랜차이즈입니다."));
        Dong dong = dongRepository.findById(dongCode)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 동 입니다."));
        Rent rent = rentRepository.findByDongId(dongCode);
        Long rentFee = floor ? rent.getFirstFloorRent() : rent.getOtherFloorRent();
        FavoriteFranchise favoriteFranchise = favoriteFranchisesRepository
                .findByMemberIdAndFranchiseIdAndDongAndSizeAndFloor(member.getId(),id,dong,size,floor)
                .orElse(null);
        Long favoriteFranchiseId = favoriteFranchise != null ? favoriteFranchise.getId() : -1;

        return FranchiseFeeDto.to(franchise,rentFee,favoriteFranchiseId);
    }
}
