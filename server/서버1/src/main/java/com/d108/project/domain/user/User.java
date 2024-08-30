package com.d108.project.domain.user;

import com.d108.project.domain.board.Board;
import com.d108.project.domain.comment.Comment;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private Long userId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

//    // 게시글
//    // 한 유저가 많은 글을 가지고 있을 수 있기 때문에 1:N
//    @OneToMany(mappedBy = "user")
//    private List<Board> boards;

//    // 댓글
//    // 한 유저가 많은 댓글을 가지고 있을 수 있기 때문에 1:N
//    // 이걸 조회 하기 싫으면 굳이 안넣어도 괜찮나?
//    @OneToMany(mappedBy = "user")
//    private List<Comment> comments;


}
