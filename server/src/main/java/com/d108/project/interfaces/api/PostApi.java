package com.d108.project.interfaces.api;

import com.d108.project.domain.forum.post.dto.PostCreateDto;
import com.d108.project.domain.forum.post.dto.PostResponseDto;
import com.d108.project.domain.forum.post.dto.PostUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/posts")
public interface PostApi {

    @GetMapping
    ResponseEntity<List<PostResponseDto>> getAllPosts();

    @GetMapping("/{postId}")
    ResponseEntity<PostResponseDto> getPostById(@PathVariable("postId") Long postId);

    @PostMapping
    ResponseEntity<Void> createPost(@RequestBody PostCreateDto postCreateDto);

    @PutMapping("/{postId}")
    ResponseEntity<Void> updatePost(@PathVariable("postId") Long postId, @RequestParam Long memberId, @RequestBody PostUpdateDto postUpdateDto);

    @DeleteMapping("/{postId}")
    ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId, @RequestParam Long memberId);
}
