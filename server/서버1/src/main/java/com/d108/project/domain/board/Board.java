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
    private Long boardId;

    // 글 제목
    private String boardTitle;

    // 글 내용
    private String boardDescription;

    // 조회수
    private Long boardView;

    // 유저와 관계 설정 (N:1) ?
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 댓글 (N:N) ?
    @ManyToMany
    @JoinTable(
            // 중계테이블 생성
            name = "board_comments",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name="comment_id")
    )
    private List<Comment> comments;

    // 생성 및 수정
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Builder
    public Board(String boardTitle, String boardDescription, Long boardView, User user) {
        this.boardTitle = boardTitle;
        this.boardDescription = boardDescription;
        this.boardView = boardView;
        this.user = user;
    }
    
    // 생성 및 수정 자동 설정
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

