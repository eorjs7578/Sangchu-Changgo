package com.d108.project.domain.forum.post.entity;

import com.d108.project.domain.forum.board.entity.Board;
import com.d108.project.domain.global.BaseTimeEntity;
import com.d108.project.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "posts")
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", updatable = false)
    private Member member;

    private String title;
    private String content;
    private Long view;

    @Builder
    public Post(Board board, Member member, String title, String content) {
        this.board = board;
        this.member = member;
        this.title = title;
        this.content = content;
        this.view = 0L;
    }
}
