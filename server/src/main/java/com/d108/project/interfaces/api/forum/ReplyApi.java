package com.d108.project.interfaces.api.forum;

import com.d108.project.domain.forum.reply.dto.ReplyByPostIdResponseDto;
import com.d108.project.domain.forum.reply.dto.ReplyCreateDto;
import com.d108.project.domain.forum.reply.dto.ReplyUpdateDto;
import com.d108.project.domain.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/posts/{postId}/replies")
public interface ReplyApi {
    @PostMapping
    ResponseEntity<Void> createReply(@AuthenticationPrincipal Member member, @PathVariable("postId") Long postId, @RequestBody ReplyCreateDto replyCreateDto);

    @GetMapping
    ResponseEntity<List<ReplyByPostIdResponseDto>> getAllReplyByPostId(@PathVariable("postId") Long postId);

    @PutMapping("/{replyId}")
    ResponseEntity<Void> updateReply(@AuthenticationPrincipal Member member, @PathVariable("postId") Long postId, @PathVariable("replyId") Long replyId, @RequestBody ReplyUpdateDto replyUpdateDto);

    @DeleteMapping("/{replyId}")
    ResponseEntity<Void> deleteReply(@AuthenticationPrincipal Member member, @PathVariable("postId") Long postId, @PathVariable("replyId") Long replyId);
}
