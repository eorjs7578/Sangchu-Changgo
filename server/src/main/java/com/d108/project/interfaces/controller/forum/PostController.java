package com.d108.project.interfaces.controller.forum;

import com.d108.project.domain.forum.post.service.PostService;
import com.d108.project.domain.forum.post.dto.PostCreateDto;
import com.d108.project.domain.forum.post.dto.PostResponseDto;
import com.d108.project.domain.forum.post.dto.PostUpdateDto;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.interfaces.api.forum.PostApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "게시판")
@RestController
@RequiredArgsConstructor
public class PostController implements PostApi {

    private final PostService postService;

    @Operation(summary = "글 작성", description =
                    "<p>시큐리티에서 인증 정보를 받아옴</p>" +
                    "<p>로그인 한 유저인지 확인해야함</p>"
    )
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

    @Operation(summary = "글 수정", description =
                    "<p>시큐리티에서 인증 정보를 받아옴</p>" +
                    "<p>로그인 한 유저인지 확인해야함</p>"
    )
    @Override
    public ResponseEntity<Void> updatePost(Long postId, Member member, PostUpdateDto postUpdateDto) {
        postService.updatePostById(postId, member, postUpdateDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "단일 글 조회")
    @Override
    public ResponseEntity<PostResponseDto> getPostById(Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @Operation(summary = "전체 글 조회")
    @Override
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @Operation(summary = "글 삭제", description =
                    "<p>시큐리티에서 인증 정보를 받아옴</p>" +
                    "<p>로그인 한 유저인지 확인해야함</p>"
    )
    @Override
    public ResponseEntity<Void> deletePost(Long postId, Member member) {
        postService.deletePostById(postId, member);
        return ResponseEntity.noContent().build();
    }

}