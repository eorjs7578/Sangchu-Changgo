package com.d108.project.domain.franchise.dto;

import com.d108.project.domain.franchise.entity.Franchise;
import lombok.Builder;

@Builder
public record FranchiseFeeDto(String name, Long franchiseFee, Long educationFee,
                              Long deposit, Long otherFee, Long interior, String link) {

    public static FranchiseFeeDto to(Franchise franchise) {
        return FranchiseFeeDto.builder().
                name(franchise.getName()).
                franchiseFee(franchise.getFranchiseFee()).
                educationFee(franchise.getEducationFee()).
                deposit(franchise.getDeposit()).
                otherFee(franchise.getOtherFee()).
                interior(franchise.getInterior()).
                link(franchise.getLink()).
                build();

    }
}
