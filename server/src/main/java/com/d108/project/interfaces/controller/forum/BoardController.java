package com.d108.project.interfaces.controller.forum;

import com.d108.project.domain.forum.board.dto.BoardRequestSearchDto;
import com.d108.project.domain.forum.board.dto.BoardResponseDto;
import com.d108.project.domain.forum.board.service.BoardService;
import com.d108.project.interfaces.api.forum.BoardAPI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "게시판")
@RestController
@RequiredArgsConstructor
public class BoardController implements BoardAPI {

    private final BoardService boardService;


    @Operation(summary = "[ALL] 모든 게시판 페이징 조회", description = "page는 0부터 시작")
    @Override
    public Page<BoardResponseDto> getAllBoards(int page, int size) {
        return boardService.getAllBoards(page, size);
    }

    @Operation(summary = "[ALL] 모든 프랜차이즈 게시판 페이징 조회", description = "page는 0부터 시작")
    @Override
    public Page<BoardResponseDto> getAllFranchiseBoards(int page, int size) {
        return boardService.getAllFranchiseBoards(page, size);
    }

    @Operation(summary = "[ALL] 모든 상권 게시판 페이징 조회", description = "page는 0부터 시작")
    @Override
    public Page<BoardResponseDto> getAllAreaBoards(int page, int size) {
        return boardService.getAllAreaBoards(page, size);
    }

    @Operation(summary = "[ALL] 매물 게시판 조회", description = "")
    @Override
    public BoardResponseDto getSaleBoard() {
        return boardService.getSaleBoard();
    }

    @Operation(summary = "[ALL] 모든 게시판 페이징 검색", description = "page는 0부터 시작")
    @Override
    public Page<BoardResponseDto> searchBoard(BoardRequestSearchDto boardRequestSearchDto, int page, int size) {
        return boardService.searchBoard(boardRequestSearchDto, page, size);
    }

    @Operation(summary = "[ALL] 모든 프랜차이즈 게시판 페이징 검색", description = "page는 0부터 시작")
    @Override
    public Page<BoardResponseDto> searchFranchiseBoard(BoardRequestSearchDto boardRequestSearchDto, int page, int size) {
        return boardService.searchFranchiseBoard(boardRequestSearchDto, page, size);
    }

    @Operation(summary = "[ALL] 모든 상권 게시판 페이징 검색", description = "page는 0부터 시작")
    @Override
    public Page<BoardResponseDto> searchAreaBoard(BoardRequestSearchDto boardRequestSearchDto, int page, int size) {
        return boardService.searchAreaBoard(boardRequestSearchDto, page, size);
    }

    @Operation(summary = "[ALL] 인기 프랜차이즈 게시판 10개 조회", description = "")
    @Override
    public List<BoardResponseDto> getPopularFranchiseBoards() {
        return boardService.getPopularFranchiseBoard();
    }


    @Operation(summary = "[ALL] 인기 상권 게시판 10개 조회", description = "")
    @Override
    public List<BoardResponseDto> getPopularAreaBoards() {
        return boardService.getPopularAreaBoard();
    }
}

