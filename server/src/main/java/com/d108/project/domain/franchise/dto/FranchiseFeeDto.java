package com.d108.project.domain.franchise.dto;

import com.d108.project.domain.franchise.entity.Franchise;
import lombok.Builder;

@Builder
public record FranchiseFeeDto(Long id,String name, Long franchiseFee, Long educationFee,
                              Long deposit, Long otherFee, Long interior, String link, Long rentFee, Long likeId) {

    public static FranchiseFeeDto to(Franchise franchise, Long rentFee, Long likeId) {
        return FranchiseFeeDto.builder().
                id(franchise.getId()).
                name(franchise.getName()).
                franchiseFee(franchise.getFranchiseFee()).
                educationFee(franchise.getEducationFee()).
                deposit(franchise.getDeposit()).
                otherFee(franchise.getOtherFee()).
                interior(franchise.getInterior()).
                link(franchise.getLink()).
                rentFee(rentFee).
                likeId(likeId).
                build();

    }

}
