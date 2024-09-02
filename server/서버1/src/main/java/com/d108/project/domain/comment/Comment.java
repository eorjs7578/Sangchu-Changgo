package com.d108.project.domain.comment;

import com.d108.project.domain.board.Board;
import com.d108.project.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@Setter
@Table(name = "comment")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", updatable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
