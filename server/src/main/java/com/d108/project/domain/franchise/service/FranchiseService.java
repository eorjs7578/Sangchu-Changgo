package com.d108.project.domain.franchise.service;

import com.d108.project.domain.franchise.dto.FranchiseDto;
import com.d108.project.domain.franchise.dto.FranchiseFeeDto;
import com.d108.project.domain.franchise.dto.FranchiseTypeDto;
import com.d108.project.domain.member.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FranchiseService {
    List<FranchiseTypeDto> getFranchiseTypes();
    List<FranchiseDto> getFranchisesByType(String type);
    List<FranchiseDto> getFranchisesByName(String name);
    List<FranchiseDto> getAllFranchises();
    FranchiseFeeDto getFranchiseFee(Member member, Long id, Long dongCode, Long size, Boolean floor);
}
