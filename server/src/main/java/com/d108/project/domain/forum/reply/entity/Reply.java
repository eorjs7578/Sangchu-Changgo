package com.d108.project.domain.forum.reply.entity;

import com.d108.project.domain.global.BaseTimeEntity;
import com.d108.project.domain.forum.post.entity.Post;
import com.d108.project.domain.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "replies")
public class Reply extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @NotNull
    private String content;

    @Builder
    public Reply(Member member, Post post, String content) {
        this.member = member;
        this.post = post;
        this.content = content;
    }
}
