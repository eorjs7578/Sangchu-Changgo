package com.d108.project.interfaces.api.forum;

import com.d108.project.domain.forum.reply.dto.ReplyByPostIdResponseDto;
import com.d108.project.domain.forum.reply.dto.ReplyCreateDto;
import com.d108.project.domain.forum.reply.dto.ReplyUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/posts/{postId}/replies")
public interface ReplyApi {
    @PostMapping
    ResponseEntity<Void> createReply(@PathVariable("postId") Long postId, @RequestBody ReplyCreateDto replyCreateDto);

    @GetMapping
    ResponseEntity<List<ReplyByPostIdResponseDto>> getAllReplyByPostId(@PathVariable("postId") Long postId);

    @PutMapping("/{replyId}")
    ResponseEntity<Void> updateReply(@PathVariable("postId") Long postId, @PathVariable("replyId") Long replyId, @RequestParam Long memberId, @RequestBody ReplyUpdateDto replyUpdateDto);

    @DeleteMapping("/{replyId}")
    ResponseEntity<Void> deleteReply(@PathVariable("postId") Long postId, @PathVariable("replyId") Long replyId, @RequestParam Long memberId);
}
