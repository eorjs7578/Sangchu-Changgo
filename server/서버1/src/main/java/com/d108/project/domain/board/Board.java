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
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments;

    // 생성 및 수정
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Builder
    public Board(String boardTitle, String boardDescription, Long boardView, Long userId) {
        this.boardTitle = boardTitle;
        this.boardDescription = boardDescription;
        this.boardView = boardView;
        this.userId = userId;
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

