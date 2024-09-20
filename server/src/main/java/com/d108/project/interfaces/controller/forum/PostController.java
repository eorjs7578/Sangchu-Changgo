package com.d108.project.interfaces.controller.forum;

import com.d108.project.domain.forum.post.service.PostService;
import com.d108.project.domain.forum.post.dto.PostCreateDto;
import com.d108.project.domain.forum.post.dto.PostResponseDto;
import com.d108.project.domain.forum.post.dto.PostUpdateDto;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.interfaces.api.forum.PostApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController implements PostApi {

    private final PostService postService;

    @Override
    public ResponseEntity<Void> createPost(@AuthenticationPrincipal Member member, PostCreateDto postCreateDto) {
        Long postId = postService.createPost(member, postCreateDto);

        // createPost 에서 글 번호 받아서, 글 번호로 redirect URL 전달하기
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{postId}")
                .buildAndExpand(postId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Void> updatePost(Long postId, Member member, PostUpdateDto postUpdateDto) {
        postService.updatePostById(postId, member, postUpdateDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<PostResponseDto> getPostById(Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @Override
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @Override
    public ResponseEntity<Void> deletePost(Long postId, Member member) {
        postService.deletePostById(postId, member);
        return ResponseEntity.noContent().build();
    }

}
