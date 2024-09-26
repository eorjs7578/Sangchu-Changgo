package com.d108.project.domain.forum.board.service;
import com.d108.project.domain.forum.board.dto.BoardRequestSearchDto;
import com.d108.project.domain.forum.board.dto.BoardResponseDto;

import java.util.List;

public interface BoardService {

    /**
     * 모든 게시판을 조회하는 메서드
     * @return 게시판 목록
     */
    List<BoardResponseDto> getAllBoards(int page, int size);

    /**
     * 모든 프랜차이즈 관련 게시판을 조회하는 메서드
     * @return 프랜차이즈 게시판 목록
     */
    List<BoardResponseDto> getAllFranchiseBoards(int page, int size);

    /**
     * 모든 지역 관련 게시판을 조회하는 메서드
     * @return 지역 게시판 목록
     */
    List<BoardResponseDto> getAllAreaBoards(int page, int size);

    /**
     * 판매 관련 게시판을 조회하는 메서드
     * @return 판매 게시판 정보
     */
    BoardResponseDto getSaleBoard();

    /**
     * 게시판 검색 메서드
     * @param boardRequestSearchDto 검색 조건 DTO
     * @return 검색 결과에 해당하는 게시판 정보
     */
    List<BoardResponseDto> searchBoard(BoardRequestSearchDto boardRequestSearchDto);

    List<BoardResponseDto> searchAreaBoard(BoardRequestSearchDto boardRequestSearchDto);

    List<BoardResponseDto> searchFranchiseBoard(BoardRequestSearchDto boardRequestSearchDto);

    List<BoardResponseDto> getPopularAreaBoard();

    List<BoardResponseDto> getPopularFranchiseBoard();

}
// A상권 검색하면 ->  -> 상권 like 상권 게시판 쭉 주는 건
// 상권 번호 1번
// 어느 게시판인진 모름.

