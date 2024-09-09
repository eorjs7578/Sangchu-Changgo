package com.d108.project.domain.favorite.favoriteBusinessArea.service;

import com.d108.project.domain.favorite.favoriteBusinessArea.entity.FavoriteBusinessArea;
import com.d108.project.domain.favorite.favoriteBusinessArea.repository.FavoriteBusinessAreaRepository;
import com.d108.project.domain.businessArea.entity.BusinessArea;
import com.d108.project.domain.businessArea.repository.BusinessAreaRepository;
import com.d108.project.domain.favorite.favoriteBusinessArea.service.FavoriteBusinessAreaService;
import com.d108.project.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteBusinessAreaServiceImpl implements FavoriteBusinessAreaService {

    private FavoriteBusinessAreaRepository favoriteBusinessAreaRepository;
    private BusinessAreaRepository businessAreaRepository;

    @Override
    public FavoriteBusinessArea createFavoriteBusinessArea(Member member, Long businessAreaId) {
        BusinessArea businessArea = businessAreaRepository.findById(businessAreaId).orElseThrow();

        FavoriteBusinessArea favorite = FavoriteBusinessArea.toFavoriteBusinessArea(member, businessArea);
        return favoriteBusinessAreaRepository.save(favorite);
    }

    @Override
    public void deleteFavoriteBusinessArea(Long id) {
        favoriteBusinessAreaRepository.deleteById(id);
    }

    @Override
    public List<Long> getFavoriteBusinessAreasByMember(Member member) {
        return favoriteBusinessAreaRepository.findAllByMember(member)
                .stream().map(FavoriteBusinessArea::getId).toList();
    }
}
