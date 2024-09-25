package com.d108.project.domain.franchise.dto;

import com.d108.project.domain.franchise.entity.Franchise;

import java.util.List;

public record FranchiseListDto(List<FranchiseDto> franchises) {
    public static FranchiseListDto createFranchiseListDto(List<Franchise> franchises) {
        return new FranchiseListDto(franchises.stream()
                .map(FranchiseDto::to)
                .toList());
    }
}
