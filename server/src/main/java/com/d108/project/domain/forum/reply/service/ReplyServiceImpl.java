package com.d108.project.domain.forum.reply.service;

import com.d108.project.domain.forum.post.entity.Post;
import com.d108.project.domain.forum.post.repository.PostRepository;
import com.d108.project.domain.forum.reply.entity.Reply;
import com.d108.project.domain.forum.reply.dto.ReplyByMemberIdResponseDto;
import com.d108.project.domain.forum.reply.dto.ReplyByPostIdResponseDto;
import com.d108.project.domain.forum.reply.dto.ReplyCreateDto;
import com.d108.project.domain.forum.reply.dto.ReplyUpdateDto;
import com.d108.project.domain.forum.reply.repository.ReplyRepository;
import com.d108.project.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;

    @Override
    public void createReply(Member member, Long postId, ReplyCreateDto replyCreateDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Reply reply = Reply.builder()
                .member(member)
                .post(post)
                .content(replyCreateDto.getContent())
                .build();

        replyRepository.save(reply);
    }

    // 글 기준으로 전체 댓글 조회
    @Override
    public List<ReplyByPostIdResponseDto> getAllReplyByPostId(Long postId) {
        List<Reply> replyList = replyRepository.findAllByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 없습니다."));

        return replyList.stream()
                .map(ReplyByPostIdResponseDto::from)
                .collect(Collectors.toList());
    }
    // 유저 기준으로 전체 댓글 조회
    @Override
    public List<ReplyByMemberIdResponseDto> getAllReplyByMemberId(Long memberId) {
        List<Reply> replyList = replyRepository.findAllByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 없습니다."));

        return replyList.stream()
                .map(ReplyByMemberIdResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public void updateReply(Member member, Long replyId, ReplyUpdateDto replyUpdateDto) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        if (!member.getId().equals(reply.getMember().getId())) {
            throw new AccessDeniedException("본인의 댓글만 수정할 수 있습니다.");
        }

        reply.setContent(replyUpdateDto.getContent());
        replyRepository.save(reply);
    }

    @Override
    public void deleteReply(Member member, Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        if (!member.getId().equals(reply.getMember().getId())) {
            throw new AccessDeniedException("본인의 댓글만 삭제할 수 있습니다.");
        }

        replyRepository.deleteById(replyId);
    }
}
