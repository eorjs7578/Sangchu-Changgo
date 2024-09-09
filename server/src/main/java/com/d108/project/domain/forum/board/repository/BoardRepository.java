package com.d108.project.domain.forum.board.repository;

import com.d108.project.domain.forum.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
