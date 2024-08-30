package com.d108.project.domain.board;

import com.d108.project.domain.board.dto.BoardCreateDto;
import com.d108.project.domain.board.dto.BoardResponseDto;
import com.d108.project.domain.board.dto.BoardUpdateDto;

import java.util.List;

// 게시글
public interface BoardService {
    // 생성
    Board createBoard(BoardCreateDto boardCreateDto);
    // 전체 조회
    List<BoardResponseDto> getBoards();
    // 개별 조회
    BoardResponseDto getBoardById(Long boardId);
    // 수정
    Board updateBoardById(Long boardId, BoardUpdateDto boardUpdateDto);
    // 삭제
    void deleteBoardById(Long boardId);
}
