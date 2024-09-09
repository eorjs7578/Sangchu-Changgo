package com.d108.project.domain.forum.board.repository;

import com.d108.project.domain.forum.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
