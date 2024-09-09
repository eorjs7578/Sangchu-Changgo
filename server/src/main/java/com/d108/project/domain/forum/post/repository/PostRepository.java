package com.d108.project.domain.forum.post.repository;

import com.d108.project.domain.forum.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
