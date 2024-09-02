package com.d108.project.domain.board;

import com.d108.project.domain.comment.Comment;
import com.d108.project.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@ToString(exclude = {"user", "comments"})
@Getter
@Setter
@Table(name = "board")
@NoArgsConstructor
public class Board {
    // ID
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    // 글 제목
    private String title;

    // 글 내용
    private String description;

    // 조회수
    private Long view;

    // 유저와 관계 설정 (N:1) ?
    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments;

    // 생성 및 수정
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;

    @Builder
    public Board(String title, String description, Long view, User user) {
        this.title = title;
        this.description = description;
        this.view = view;
        this.user = user;
    }

    // 생성 및 수정 자동 설정
    @PrePersist
    protected void onCreate() {
        this.updatedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

