package com.d108.project.domain.franchise.service;

import com.d108.project.domain.franchise.dto.FranchiseDto;
import com.d108.project.domain.franchise.dto.FranchiseFeeDto;
import com.d108.project.domain.franchise.dto.FranchiseTypeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FranchiseService {
    List<FranchiseTypeDto> getFranchiseTypes();
    List<FranchiseDto> getAllFranchises();
    FranchiseFeeDto getFranchiseFee(Long id);
}
