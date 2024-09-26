package com.d108.project.domain.forum.board.repository;

import com.d108.project.domain.forum.board.dto.BoardRequestSearchDto;
import com.d108.project.domain.forum.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 프랜차이즈 게시판들 조회 (프랜차이즈 ID가 있는 게시판)
    Page<Board> findByFranchiseIdIsNotNull(Pageable pageable);

    // 지역 게시판들 조회 (상권 ID가 있는 게시판)
    Page<Board> findByAreaIdIsNotNull(Pageable pageable);

    // 특정 판매 관련 게시판 조회
    @Query("SELECT b FROM Board b WHERE b.franchise IS NULL and b.area IS NULL") // 판매 게시판 조회 로직은 수정 필요
    Optional<Board> findSaleBoard();

//    // 검색 조건에 맞는 게시판 조회
//    @Query("SELECT b FROM Board b WHERE b.areaId = :#{#request.areaId} OR b.franchiseId = :#{#request.franchiseId}")
//    List<Board> searchBoard(BoardRequestSearchDto request);
}
