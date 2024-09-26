package com.d108.project.domain.forum.board.entity;

import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.franchise.entity.Franchise;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;


    @ManyToOne
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;

}
