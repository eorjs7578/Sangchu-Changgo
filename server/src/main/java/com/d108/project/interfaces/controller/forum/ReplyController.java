package com.d108.project.interfaces.controller.forum;

import com.d108.project.domain.forum.reply.ReplyService;
import com.d108.project.domain.forum.reply.dto.ReplyByPostIdResponseDto;
import com.d108.project.domain.forum.reply.dto.ReplyCreateDto;
import com.d108.project.domain.forum.reply.dto.ReplyUpdateDto;
import com.d108.project.interfaces.api.forum.ReplyApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "댓글")
@RestController
@RequiredArgsConstructor
public class ReplyController implements ReplyApi {

    private final ReplyService replyService;


    @Operation(summary = "댓글 작성", description = "postId는 요청 주소를 통해서 보낸다.")
    @Override
    public ResponseEntity<Void> createReply(Long postId, ReplyCreateDto replyCreateDto) {
        replyService.createReply(postId, replyCreateDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "작성 글 댓글 조회", description = "게시글 조회에 포함된 기능이라 사용할 필요 없어보임")
    @Override
    public ResponseEntity<List<ReplyByPostIdResponseDto>> getAllReplyByPostId(Long postId) {
        return ResponseEntity.ok(replyService.getAllReplyByPostId(postId));
    }

    @Operation(summary = "댓글 수정", description =
                    "<p>시큐리티에서 인증 정보를 받아옴</p>" +
                    "<p>로그인 한 유저인지 확인해야함</p>"
    )
    @Override
    public ResponseEntity<Void> updateReply(Long postId, Long replyId, Long memberId, ReplyUpdateDto replyUpdateDto) {
        replyService.updateReply(replyId, memberId, replyUpdateDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "댓글 삭제", description = "postId, replyId는 요청 주소를 통해서, memberId는 파라미터를 통해서 보낸다.")
    @Override
    public ResponseEntity<Void> deleteReply(Long postId, Long replyId, Long memberId) {
        replyService.deleteReply(replyId, memberId);
        return ResponseEntity.noContent().build();
    }
}
