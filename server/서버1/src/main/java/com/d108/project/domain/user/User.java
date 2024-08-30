package com.d108.project.domain.user;

import com.d108.project.domain.board.Board;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Table(name = "user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String username;

    private String password;

    private LocalDateTime createdAt;

    private Boolean isDeleted;

    // 게시글
    // 한 유저가 많은 글을 가지고 있을 수 있기 때문에 1:N
    @OneToMany(mappedBy = "user")
    private List<Board> boards;
}
