package com.d108.project.domain.forum.post.entity;

import com.d108.project.domain.forum.board.entity.Board;
import com.d108.project.domain.forum.reply.entity.Reply;
import com.d108.project.domain.global.BaseTimeEntity;
import com.d108.project.domain.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


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

    // 댓글 역참조
    @OneToMany(mappedBy="post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies;

    @NotNull
    private String title;
    @NotNull
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
