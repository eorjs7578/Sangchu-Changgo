package com.d108.project.domain.franchise.domain;

import com.d108.project.domain.favorite.favoriteFranchise.domain.FavoriteFranchise;
import com.d108.project.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "franchises")
public class Franchise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 이름
    private String name;

    // 이름
    private String description;

    @OneToMany(mappedBy = "franchise")
    private List<FavoriteFranchise> FavoriteFranchises = new ArrayList<>();
}
