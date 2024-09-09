package com.d108.project.interfaces.controller;

import com.d108.project.domain.forum.post.service.PostService;
import com.d108.project.domain.forum.post.dto.PostCreateDto;
import com.d108.project.domain.forum.post.dto.PostDeleteDto;
import com.d108.project.domain.forum.post.dto.PostResponseDto;
import com.d108.project.domain.forum.post.dto.PostUpdateDto;
import com.d108.project.domain.forum.reply.ReplyService;
import com.d108.project.domain.forum.reply.dto.ReplyByPostIdResponseDto;
import com.d108.project.domain.forum.reply.dto.ReplyCreateDto;
import com.d108.project.domain.forum.reply.dto.ReplyUpdateDto;
import com.d108.project.interfaces.api.PostApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController implements PostApi {

    private final PostService postService;
    private final ReplyService replyService;

    @Override
    public ResponseEntity<Void> createPost(PostCreateDto postCreateDto) {
        Long postId = postService.createPost(postCreateDto);

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

    @Override
    public ResponseEntity<Void> createReply(Long postId, ReplyCreateDto replyCreateDto) {
        replyService.createReply(postId, replyCreateDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<ReplyByPostIdResponseDto>> getAllReplyByPostId(Long postId) {
        return ResponseEntity.ok(replyService.getAllReplyByPostId(postId));
    }

    @Override
    public ResponseEntity<Void> updateReply(Long postId, Long replyId, Long memberId, ReplyUpdateDto replyUpdateDto) {
        replyService.updateReply(replyId, memberId, replyUpdateDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteReply(Long postId, Long replyId, Long memberId) {
        replyService.deleteReply(replyId, memberId);
        return ResponseEntity.noContent().build();
    }
}
