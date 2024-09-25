package com.d108.project.interfaces.controller.forum;

import com.d108.project.domain.forum.post.service.PostService;
import com.d108.project.domain.forum.post.dto.PostCreateDto;
import com.d108.project.domain.forum.post.dto.PostResponseDto;
import com.d108.project.domain.forum.post.dto.PostUpdateDto;
import com.d108.project.interfaces.api.forum.PostApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "게시글")
@RestController
@RequiredArgsConstructor
public class PostController implements PostApi {

    private final PostService postService;

    @Override
    public ResponseEntity<Void> createPost(Member member, PostCreateDto postCreateDto) {
        Long postId = postService.createPost(member, postCreateDto);

        // createPost 에서 글 번호 받아서, 글 번호로 redirect URL 전달하기
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{postId}")
                .buildAndExpand(postId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Void> updatePost(Long postId, Long memberId, PostUpdateDto postUpdateDto) {
        postService.updatePostById(postId, memberId, postUpdateDto);
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
    public ResponseEntity<Void> deletePost(Long postId, Long memberId) {
        postService.deletePostById(postId, memberId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "상권 게시판 조회", description =
                    "<p>상권 id로 만들어진 게시글을 조회</p>"
    )
    @Override
    public ResponseEntity<List<PostResponseDto>> getAllPostsByAreaId(Long areaId) {
        return ResponseEntity.ok(postService.getAllPostsByAreaId(areaId));
    }

    @Operation(summary = "프랜차이즈 게시판 조회", description =
            "<p>프랜차이즈 id로 만들어진 게시글을 조회</p>"
    )
    @Override
    public ResponseEntity<List<PostResponseDto>> getAllPostsByFranchiseId(Long franchiseId) {
        return ResponseEntity.ok(postService.getAllPostsByFranchiseId(franchiseId));
    }
}
