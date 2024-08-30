package com.d108.project.domain.comment;

import com.d108.project.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@Setter
@Table(name = "comment")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    private String commentContent;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
