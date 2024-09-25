package com.d108.project.interfaces.controller.forum;

import com.d108.project.domain.forum.board.dto.BoardRequestSearchDto;
import com.d108.project.domain.forum.board.dto.BoardResponseDto;
import com.d108.project.domain.forum.board.service.BoardService;
import com.d108.project.interfaces.api.forum.BoardAPI;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "게시판")
@RestController
@RequiredArgsConstructor
public class BoardController implements BoardAPI {

    private final BoardService boardService;

    @Override
    public List<BoardResponseDto> getAllBoards() {
        return boardService.getAllBoards();
    }

    @Override
    public List<BoardResponseDto> getAllFranchiseBoards() {
        return boardService.getAllFranchiseBoards();
    }

    @Override
    public List<BoardResponseDto> getAllAreaBoards() {
        return boardService.getAllAreaBoards();
    }

    @Override
    public BoardResponseDto getSaleBoard() {
        return boardService.getSaleBoard();
    }

    @Override
    public BoardResponseDto searchBoard(BoardRequestSearchDto boardRequestSearchDto) {
        return boardService.searchBoard(boardRequestSearchDto);
    }
}
