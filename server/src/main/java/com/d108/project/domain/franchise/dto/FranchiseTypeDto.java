package com.d108.project.domain.franchise.dto;

import com.d108.project.domain.franchise.entity.Franchise;
import lombok.Builder;

@Builder
public record FranchiseTypeDto(String type) {
    public static FranchiseTypeDto to(Franchise franchise) {
        return FranchiseTypeDto.builder()
                .type(franchise.getType())
                .build();
    }
    public static FranchiseTypeDto to(String type) {
        return FranchiseTypeDto.builder()
                .type(type)
                .build();
    }
}
