package com.d108.project.domain.favorite.favoriteFranchise.dto;

import lombok.Builder;

@Builder
public record FavoriteFranchiseRequestDto(Long franchiseId,
                                          Long dongCode,
                                          Long storeSize,
                                          Boolean floor) {
}
