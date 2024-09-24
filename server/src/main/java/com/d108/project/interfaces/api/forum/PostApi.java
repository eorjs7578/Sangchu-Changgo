package com.d108.project.interfaces.api.forum;

import com.d108.project.domain.forum.post.dto.PostCreateDto;
import com.d108.project.domain.forum.post.dto.PostResponseDto;
import com.d108.project.domain.forum.post.dto.PostUpdateDto;
import com.d108.project.domain.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/pi/postsa")
public interface PostApi {

    @GetMapping
    ResponseEntity<List<PostResponseDto>> getAllPosts();

    @GetMapping("/{postId}")
    ResponseEntity<PostResponseDto> getPostById(@PathVariable("postId") Long postId);

    @PostMapping
    ResponseEntity<Void> createPost(@AuthenticationPrincipal Member member, @RequestBody PostCreateDto postCreateDto);

    @PutMapping("/{postId}")
    ResponseEntity<Void> updatePost(@PathVariable("postId") Long postId, @AuthenticationPrincipal Member member, @RequestBody PostUpdateDto postUpdateDto);

    @DeleteMapping("/{postId}")
    ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId, @AuthenticationPrincipal Member member);
}
