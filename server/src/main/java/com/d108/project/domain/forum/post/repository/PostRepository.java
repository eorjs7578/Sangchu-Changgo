package com.d108.project.domain.forum.post.repository;

import com.d108.project.domain.forum.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT post.id FROM Post post")
    List<Long> findAllPostIds();
}
