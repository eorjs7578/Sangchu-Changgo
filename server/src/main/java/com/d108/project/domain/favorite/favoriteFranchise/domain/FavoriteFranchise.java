package com.d108.project.domain.favorite.favoriteFranchise.domain;

import com.d108.project.domain.franchise.domain.Franchise;
import com.d108.project.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "favorite_franchises")
public class FavoriteFranchise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 관심상권 PK

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;

}