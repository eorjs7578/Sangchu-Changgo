package com.d108.project.domain.franchise.dto;

import com.d108.project.domain.franchise.entity.Franchise;

import java.util.ArrayList;
import java.util.List;

public record FranchiseListDto(List<FranchiseDto> franchises) {
    public static FranchiseListDto createFranchiseListDto(List<Franchise> franchises) {
        List<FranchiseDto> list = new ArrayList<>();
        for (Franchise franchise : franchises) {
            list.add(FranchiseDto.to(franchise));
        }
        return new FranchiseListDto(list);
    }

}
