package com.d108.project.domain.franchise.dto;

import com.d108.project.domain.franchise.entity.Franchise;
import lombok.Builder;

import java.util.List;

@Builder
public record FranchiseListDto(List<FranchiseDto> franchises) {
    public static FranchiseListDto createFranchiseListDto(List<Franchise> franchises) {
        return new FranchiseListDto(franchises.stream()
                .map(FranchiseDto::to)
                .toList());
    }
}
