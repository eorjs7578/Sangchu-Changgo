package com.d108.project.interfaces.api;

import com.d108.project.domain.forum.post.dto.PostCreateDto;
import com.d108.project.domain.forum.post.dto.PostDeleteDto;
import com.d108.project.domain.forum.post.dto.PostResponseDto;
import com.d108.project.domain.forum.post.dto.PostUpdateDto;
import com.d108.project.domain.forum.reply.dto.ReplyByPostIdResponseDto;
import com.d108.project.domain.forum.reply.dto.ReplyCreateDto;
import com.d108.project.domain.forum.reply.dto.ReplyUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: 얘네 분리해야겠지..? 근데 어케 함
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

    @PostMapping("/{postId}/replies")
    ResponseEntity<Void> createReply(@PathVariable("postId") Long postId, @RequestBody ReplyCreateDto replyCreateDto);

    @GetMapping("/{postId}/replies")
    ResponseEntity<List<ReplyByPostIdResponseDto>> getAllReplyByPostId(@PathVariable("postId") Long postId);

    @PutMapping("/{postId}/replies/{replyId}")
    ResponseEntity<Void> updateReply(@PathVariable("postId") Long postId, @PathVariable("replyId") Long replyId, @RequestParam Long memberId, @RequestBody ReplyUpdateDto replyUpdateDto);

    @DeleteMapping("/{postId}/replies/{replyId}")
    ResponseEntity<Void> deleteReply(@PathVariable("postId") Long postId, @PathVariable("replyId") Long replyId, @RequestParam Long memberId);
}
