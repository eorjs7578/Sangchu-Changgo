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
    ResponseEntity<PostResponseDto> getPostById(@PathVariable("postId") Integer postId);

    @PostMapping
    ResponseEntity<Void> createPost(@RequestBody PostCreateDto postCreateDto);

    @PutMapping("/{postId}")
    ResponseEntity<Void> updatePost(@PathVariable("postId") Integer postId, @RequestParam Integer memberId, @RequestBody PostUpdateDto postUpdateDto);

    @DeleteMapping("/{postId}")
    ResponseEntity<Void> deletePost(@PathVariable("postId") Integer postId, @RequestParam Integer memberId);

    @PostMapping("/{postId}/replies")
    ResponseEntity<Void> createReply(@PathVariable("postId") Integer postId, @RequestBody ReplyCreateDto replyCreateDto);

    @GetMapping("/{postId}/replies")
    ResponseEntity<List<ReplyByPostIdResponseDto>> getAllReplyByPostId(@PathVariable("postId") Integer postId);

    @PutMapping("/{postId}/replies/{replyId}")
    ResponseEntity<Void> updateReply(@PathVariable("postId") Integer postId, @PathVariable("replyId") Integer replyId, @RequestParam Integer memberId, @RequestBody ReplyUpdateDto replyUpdateDto);

    @DeleteMapping("/{postId}/replies/{replyId}")
    ResponseEntity<Void> deleteReply(@PathVariable("postId") Integer postId, @PathVariable("replyId") Integer replyId, @RequestParam Integer memberId);
}
