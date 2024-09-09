package com.d108.project.domain.franchise.service;

import com.d108.project.domain.franchise.dto.FranchiseListDto;
import com.d108.project.domain.franchise.entity.Franchise;
import com.d108.project.domain.franchise.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FranchiseServiceImpl implements FranchiseService {
    private final FranchiseRepository franchiseRepository;

    @Override
    public FranchiseListDto getFranchiseList(List<Long> franchiseIds) {
        List<Franchise> franchises = franchiseRepository.findAllById(franchiseIds);
        return FranchiseListDto.to(franchises);
    }
}
