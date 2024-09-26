package com.d108.project.domain.favorite.favoriteFranchise.entity;

import com.d108.project.domain.franchise.entity.Franchise;
import com.d108.project.domain.global.BaseTimeEntity;
import com.d108.project.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "favorite_franchises")
public class FavoriteFranchise extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 관심상권 PK

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;

    public static FavoriteFranchise toFavoriteFranchise(Member member, Franchise franchise) {
        FavoriteFranchise favoriteFranchise = new FavoriteFranchise();
        favoriteFranchise.setMember(member);
        favoriteFranchise.setFranchise(franchise);
        return favoriteFranchise;
    }

}
