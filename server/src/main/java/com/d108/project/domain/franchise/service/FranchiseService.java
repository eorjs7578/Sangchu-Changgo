package com.d108.project.domain.franchise.service;

import com.d108.project.domain.franchise.dto.FranchiseListDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FranchiseService {
    FranchiseListDto getFranchiseList(List<Long> FranchiseIds);
}
