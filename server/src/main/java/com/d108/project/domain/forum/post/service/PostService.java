package com.d108.project.domain.forum.post.service;

import com.d108.project.domain.forum.post.dto.PostDeleteDto;
import com.d108.project.domain.forum.post.dto.PostUpdateDto;
import com.d108.project.domain.forum.post.dto.PostCreateDto;
import com.d108.project.domain.forum.post.dto.PostResponseDto;

import java.util.List;

public interface PostService {

    Long createPost(PostCreateDto postCreateDto);

    List<PostResponseDto> getAllPosts();

    PostResponseDto getPostById(Long id);

    void updatePostById(Long postId, Long memberId, PostUpdateDto postUpdateDto);

    void deletePostById(Long postId, Long memberId);
}
