package com.d108.project.domain.businessArea.entity;

import com.d108.project.domain.favorite.favoriteBusinessArea.entity.FavoriteBusinessArea;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "business_areas")
public class BusinessArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 이름
    private String name;
    
    // 설명
    private String description;

    @OneToMany(mappedBy = "businessArea")
    private List<FavoriteBusinessArea> FavoriteBusinessAreas = new ArrayList<>();
}
