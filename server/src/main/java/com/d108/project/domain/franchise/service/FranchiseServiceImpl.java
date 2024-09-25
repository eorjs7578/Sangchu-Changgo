package com.d108.project.domain.franchise.service;

import com.d108.project.domain.franchise.dto.FranchiseDto;
import com.d108.project.domain.franchise.dto.FranchiseFeeDto;
import com.d108.project.domain.franchise.dto.FranchiseTypeDto;
import com.d108.project.domain.franchise.entity.Franchise;
import com.d108.project.domain.franchise.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FranchiseServiceImpl implements FranchiseService {

    private final FranchiseRepository franchiseRepository;

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
    public FranchiseFeeDto getFranchiseFee(Long id) {
        Franchise franchise = franchiseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 프랜차이즈입니다."));
        return FranchiseFeeDto.to(franchise);
    }
}
