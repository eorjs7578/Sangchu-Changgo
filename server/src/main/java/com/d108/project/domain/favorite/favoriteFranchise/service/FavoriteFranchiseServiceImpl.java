package com.d108.project.domain.favorite.favoriteFranchise.service;

import com.d108.project.domain.favorite.favoriteFranchise.entity.FavoriteFranchise;
import com.d108.project.domain.favorite.favoriteFranchise.repository.FavoriteFranchiseRepository;
import com.d108.project.domain.franchise.entity.Franchise;
import com.d108.project.domain.franchise.repository.FranchiseRepository;
import com.d108.project.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteFranchiseServiceImpl implements FavoriteFranchiseService {

    private FavoriteFranchiseRepository favoriteFranchiseRepository;
    private FranchiseRepository franchiseRepository;

    @Override
    public FavoriteFranchise createFavoriteFranchise(Member member, Long franchiseId) {
        Franchise franchise = franchiseRepository.findById(franchiseId).orElseThrow();

        FavoriteFranchise favoriteFranchise = FavoriteFranchise.toFavoriteFranchise(member, franchise);

        return favoriteFranchiseRepository.save(favoriteFranchise);
    }

    @Override
    public void deleteFavoriteFranchise(Long id) {
        favoriteFranchiseRepository.deleteById(id);
    }

    @Override
    public List<FavoriteFranchise> getFavoriteFranchisesByMember(Member member) {
        return favoriteFranchiseRepository.findAllByMember(member);
    }
}
