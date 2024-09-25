package com.d108.project.interfaces.api.forum;

import com.d108.project.domain.forum.board.dto.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/boards")
public interface BoardAPI {

    @GetMapping("/all")
    List<BoardResponseDto> getAllBoards();
    @GetMapping("/all/franchise")
    List<BoardResponseDto> getAllFranchiseBoards();
    @GetMapping("/all/area")
    List<BoardResponseDto> getAllAreaBoards();
    @GetMapping("/sale")
    BoardResponseDto getSaleBoard();
    @GetMapping("/search")
    BoardResponseDto searchBoard(BoardRequestSearchDto boardRequestSearchDto);
}
