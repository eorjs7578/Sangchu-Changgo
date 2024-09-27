package com.d108.project.interfaces.api.forum;

import com.d108.project.domain.forum.post.dto.PostCreateDto;
import com.d108.project.domain.forum.post.dto.PostPageResponseDto;
import com.d108.project.domain.forum.post.dto.PostResponseDto;
import com.d108.project.domain.forum.post.dto.PostUpdateDto;
import com.d108.project.domain.member.entity.Member;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/posts")
public interface PostApi {

    @GetMapping
    ResponseEntity<List<PostResponseDto>> getAllPosts();

    @PostMapping
    ResponseEntity<Void> createPost(@AuthenticationPrincipal Member member, @RequestBody PostCreateDto postCreateDto);

    @GetMapping("/{postId}")
    ResponseEntity<PostResponseDto> getPostById(@PathVariable("postId") Long postId);

    @PutMapping("/{postId}")
    ResponseEntity<Void> updatePost(@AuthenticationPrincipal Member member, @PathVariable("postId") Long postId, @RequestBody PostUpdateDto postUpdateDto);

    @DeleteMapping("/{postId}")
    ResponseEntity<Void> deletePost(@AuthenticationPrincipal Member member, @PathVariable("postId") Long postId);

    @GetMapping("/area/{areaId}")
    ResponseEntity<List<PostResponseDto>> getAllPostsByAreaId(@PathVariable("areaId") Long areaId);

    @GetMapping("/franchise/{franchiseId}")
    ResponseEntity<List<PostResponseDto>> getAllPostsByFranchiseId(@PathVariable("franchiseId") Long franchiseId);

    @GetMapping("/{boardId}/{page}/{size}")
    public ResponseEntity<?> getPostsByBoardId(
        @PathVariable Long boardId,  // URL 경로의 boardId
        @PathVariable Integer page,  // URL 경로의 page
        @PathVariable Integer size   // URL 경로의 size
    );
}


