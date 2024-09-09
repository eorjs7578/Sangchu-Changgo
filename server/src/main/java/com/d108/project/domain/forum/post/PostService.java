package com.d108.project.domain.forum.post;

import com.d108.project.domain.forum.post.dto.PostDeleteDto;
import com.d108.project.domain.forum.post.dto.PostUpdateDto;
import com.d108.project.domain.forum.post.dto.PostCreateDto;
import com.d108.project.domain.forum.post.dto.PostResponseDto;

import java.util.List;

public interface PostService {

    Integer createPost(PostCreateDto postCreateDto);

    List<PostResponseDto> getAllPosts();

    PostResponseDto getPostById(Integer id);

    void updatePostById(Integer postId, Integer memberId, PostUpdateDto postUpdateDto);

    void deletePostById(Integer postId, Integer memberId);
}
