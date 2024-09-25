package com.d108.project.interfaces.api.forum;

import com.d108.project.domain.forum.board.dto.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/boards")
public interface BoardAPI {

    @GetMapping("/all")
    List<BoardResponseDto> getAllBoards(int page, int size);
    @GetMapping("/all/franchise")
    List<BoardResponseDto> getAllFranchiseBoards(int page, int size);
    @GetMapping("/all/area")
    List<BoardResponseDto> getAllAreaBoards(int page, int size);
    @GetMapping("/sale")
    BoardResponseDto getSaleBoard();
    @GetMapping("/search")
    List<BoardResponseDto> searchBoard(BoardRequestSearchDto boardRequestSearchDto);
    @GetMapping("/search/area")
    List<BoardResponseDto> searchAreaBoard(BoardRequestSearchDto boardRequestSearchDto);
    @GetMapping("/search/franchise")
    List<BoardResponseDto> searchFranchiseBoard(BoardRequestSearchDto boardRequestSearchDto);

}
