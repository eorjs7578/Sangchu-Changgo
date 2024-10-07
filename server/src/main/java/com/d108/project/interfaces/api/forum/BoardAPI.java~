package com.d108.project.interfaces.api.forum;

import com.d108.project.domain.forum.board.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/boards")
public interface BoardAPI {

    @GetMapping("/all/{page}/{size}")
    Page<BoardResponseDto> getAllBoards(@PathVariable int page, @PathVariable int size);

    @GetMapping("/all/franchise/{page}/{size}")
    Page<BoardResponseDto> getAllFranchiseBoards(@PathVariable int page, @PathVariable int size);

    @GetMapping("/all/area/{page}/{size}")
    Page<BoardResponseDto> getAllAreaBoards(@PathVariable int page, @PathVariable int size);

    @GetMapping("/sale")
    BoardResponseDto getSaleBoard();

    @GetMapping("/search/{page}/{size}")
    Page<BoardResponseDto> searchBoard(BoardRequestSearchDto boardRequestSearchDto, @PathVariable int page, @PathVariable int size);

    @GetMapping("/search/area/{page}/{size}")
    Page<BoardResponseDto> searchAreaBoard(BoardRequestSearchDto boardRequestSearchDto, @PathVariable int page, @PathVariable int size);

    @GetMapping("/search/franchise/{page}/{size}")
    Page<BoardResponseDto> searchFranchiseBoard(BoardRequestSearchDto boardRequestSearchDto, @PathVariable int page, @PathVariable int size);


    // 인기 게시판 각각 프랜차이즈, 상권
    @GetMapping("/popular/franchise")
    List<BoardResponseDto> getPopularFranchiseBoards();

    @GetMapping("/popular/area")
    List<BoardResponseDto> getPopularAreaBoards();
}
