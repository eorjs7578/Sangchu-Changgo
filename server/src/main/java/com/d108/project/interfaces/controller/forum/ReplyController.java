package com.d108.project.interfaces.controller.forum;

import com.d108.project.domain.forum.reply.ReplyService;
import com.d108.project.domain.forum.reply.dto.ReplyByPostIdResponseDto;
import com.d108.project.domain.forum.reply.dto.ReplyCreateDto;
import com.d108.project.domain.forum.reply.dto.ReplyUpdateDto;
import com.d108.project.interfaces.api.forum.ReplyApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReplyController implements ReplyApi {

    private final ReplyService replyService;

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
