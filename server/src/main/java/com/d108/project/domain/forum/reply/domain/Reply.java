package com.d108.project.domain.forum.reply.domain;

import com.d108.project.domain.common.BaseTimeEntity;
import com.d108.project.domain.forum.board.domain.Board;
import com.d108.project.domain.forum.post.domain.Post;
import com.d108.project.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "replies")
public class Reply extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String content;

    @Builder
    public Reply(Member member, Post post, String content) {
        this.member = member;
        this.post = post;
        this.content = content;
    }
}
