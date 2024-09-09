package com.d108.project.domain.forum.reply;


import com.d108.project.domain.forum.reply.dto.ReplyByMemberIdResponseDto;
import com.d108.project.domain.forum.reply.dto.ReplyByPostIdResponseDto;
import com.d108.project.domain.forum.reply.dto.ReplyCreateDto;
import com.d108.project.domain.forum.reply.dto.ReplyUpdateDto;

import java.util.List;

public interface ReplyService {

    void createReply(Integer postId, ReplyCreateDto replyCreateDto);

    List<ReplyByPostIdResponseDto> getAllReplyByPostId(Integer postId);

    // 얘는 어디로 붙이지 (MemberApi vs PostApi)
    List<ReplyByMemberIdResponseDto> getAllReplyByMemberId(Integer memberId);

    void updateReply(Integer replyId, Integer memberId, ReplyUpdateDto replyUpdateDto);

    void deleteReply(Integer replyId, Integer memberId);
}
