package com.d108.project.domain.franchise.entity;

import com.d108.project.domain.favorite.favoriteFranchise.entity.FavoriteFranchise;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "franchises")
public class Franchise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이름
    private String name;

    // 이름
    private String description;

    @OneToMany(mappedBy = "franchise")
    private List<FavoriteFranchise> FavoriteFranchises = new ArrayList<>();
}
