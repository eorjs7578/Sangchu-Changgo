package com.d108.project.domain.area.entity;

import com.d108.project.domain.favorite.favoriteArea.entity.FavoriteArea;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "area")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 이름
    private String name;
    
    // 설명
    private String description;

    @OneToMany(mappedBy = "area")
    private List<FavoriteArea> FavoriteAreas = new ArrayList<>();
}
