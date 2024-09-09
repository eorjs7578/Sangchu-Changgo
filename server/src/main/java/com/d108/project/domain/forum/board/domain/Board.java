package com.d108.project.domain.forum.board.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // TODO: 상권, 프랜차이즈 - 게시판 연결
    private Integer businessAreaId;

    private Integer franchiseId;
}
