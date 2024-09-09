package com.d108.project.domain.franchise.dto;

import com.d108.project.domain.franchise.entity.Franchise;

public record FranchiseDto(String name) {
    public static FranchiseDto to(Franchise franchise) {
        return new FranchiseDto(franchise.getName());
    }
}
