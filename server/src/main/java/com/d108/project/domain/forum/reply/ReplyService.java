package com.d108.project.domain.forum.reply;


import com.d108.project.domain.forum.reply.dto.ReplyByMemberIdResponseDto;
import com.d108.project.domain.forum.reply.dto.ReplyByPostIdResponseDto;
import com.d108.project.domain.forum.reply.dto.ReplyCreateDto;
import com.d108.project.domain.forum.reply.dto.ReplyUpdateDto;
import com.d108.project.domain.member.entity.Member;

import java.util.List;

public interface ReplyService {

    void createReply(Member member, Long postId, ReplyCreateDto replyCreateDto);

    List<ReplyByPostIdResponseDto> getAllReplyByPostId(Long postId);

    // TODO: 얘는 어디로 붙이지 (MemberApi vs PostApi)
    List<ReplyByMemberIdResponseDto> getAllReplyByMemberId(Long memberId);

    void updateReply(Member member, Long replyId, ReplyUpdateDto replyUpdateDto);

    void deleteReply(Member member, Long replyId);
}
