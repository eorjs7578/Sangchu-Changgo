package com.d108.project.interfaces.controller.forum;

import com.d108.project.domain.forum.post.dto.PostPageResponseDto;
import com.d108.project.domain.forum.post.service.PostService;
import com.d108.project.domain.forum.post.dto.PostCreateDto;
import com.d108.project.domain.forum.post.dto.PostResponseDto;
import com.d108.project.domain.forum.post.dto.PostUpdateDto;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.interfaces.api.forum.PostApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "게시글")
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
    public ResponseEntity<Void> updatePost( Member member, Long postId, PostUpdateDto postUpdateDto) {
        postService.updatePostById(member, postId, postUpdateDto);
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
    public ResponseEntity<Void> deletePost(Member member, Long postId) {
        postService.deletePostById(member, postId);
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


    @Operation(summary = "[ALL] 모든 페이징 게시판 조회", description ="page는 0부터 시작")
    @Override
    public ResponseEntity<?> getPostsByBoardId(
        @PathVariable Long boardId,  // URL 경로의 boardId
        @PathVariable Integer page,  // URL 경로의 page
        @PathVariable Integer size   // URL 경로의 size
    ) {
        // 파라미터가 null일 경우 기본값 설정R
        if (page == 0) {
            page = 0; // 기본 페이지 번호
        }
        if (size == 0) {
            size = 10; // 기본 페이지 크기
        }
        return ResponseEntity.ok(postService.getPostsByBoardId(boardId, page, size));
    }
}

