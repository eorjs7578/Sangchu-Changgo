package com.d108.project.domain.forum.reply.repository;

import com.d108.project.domain.forum.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    Optional<List<Reply>> findAllByPostId(Integer postId);

    Optional<List<Reply>> findAllByMemberId(Integer memberId);
}
