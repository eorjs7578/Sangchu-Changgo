package com.d108.project.domain.forum.reply.repository;

import com.d108.project.domain.forum.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<List<Reply>> findAllByPostId(Long postId);

    Optional<List<Reply>> findAllByMemberId(Long memberId);
}
