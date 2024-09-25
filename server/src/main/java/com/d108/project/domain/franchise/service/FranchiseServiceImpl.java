package com.d108.project.domain.franchise.service;

import com.d108.project.domain.franchise.dto.FranchiseDto;
import com.d108.project.domain.franchise.dto.FranchiseFeeDto;
import com.d108.project.domain.franchise.dto.FranchiseTypeDto;
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
    public List<FranchiseDto> getAllFranchises() {
        return List.of();
    }

    @Override
    public FranchiseFeeDto getFranchiseFee(Long id) {
        return null;
    }
}
