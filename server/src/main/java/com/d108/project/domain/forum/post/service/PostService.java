package com.d108.project.domain.forum.post.service;

import com.d108.project.domain.forum.post.dto.PostUpdateDto;
import com.d108.project.domain.forum.post.dto.PostCreateDto;
import com.d108.project.domain.forum.post.dto.PostResponseDto;
import com.d108.project.domain.member.entity.Member;

import java.util.List;

public interface PostService {

    Long createPost(Member member, PostCreateDto postCreateDto);

    List<PostResponseDto> getAllPosts();

    PostResponseDto getPostById(Long postId);

    void updatePostById(Member member, Long postId, PostUpdateDto postUpdateDto);

    void deletePostById(Member member, Long postId);

    void syncCountToDatabase(Long postId);

    List<Long> getAllPostIds();

    List<PostResponseDto> getAllPostsByAreaId(Long areaId);

    List<PostResponseDto> getAllPostsByFranchiseId(Long franchiseId);
}
