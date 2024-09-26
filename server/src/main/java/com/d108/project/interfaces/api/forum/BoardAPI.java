package com.d108.project.interfaces.api.forum;

import com.d108.project.domain.forum.board.dto.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/boards")
public interface BoardAPI {

    @GetMapping("/all/{page}/{size}")
    List<BoardResponseDto> getAllBoards(@PathVariable int page, @PathVariable int size);

    @GetMapping("/all/franchise/{page}/{size}")
    List<BoardResponseDto> getAllFranchiseBoards(@PathVariable int page, @PathVariable int size);

    @GetMapping("/all/area/{page}/{size}")
    List<BoardResponseDto> getAllAreaBoards(@PathVariable int page, @PathVariable int size);

    @GetMapping("/sale")
    BoardResponseDto getSaleBoard();

    @GetMapping("/search")
    List<BoardResponseDto> searchBoard(BoardRequestSearchDto boardRequestSearchDto);

    @GetMapping("/search/area")
    List<BoardResponseDto> searchAreaBoard(BoardRequestSearchDto boardRequestSearchDto);

    @GetMapping("/search/franchise")
    List<BoardResponseDto> searchFranchiseBoard(BoardRequestSearchDto boardRequestSearchDto);


    // 인기 게시판 각각 프랜차이즈, 상권
    @GetMapping("/popular/franchise")
    List<BoardResponseDto> getPopularFranchiseBoards();

    @GetMapping("/popular/area")
    List<BoardResponseDto> getPopularAreaBoards();
}
