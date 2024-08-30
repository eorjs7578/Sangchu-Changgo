package com.d108.project.interfaces.controllers;

import com.d108.project.domain.board.Board;
import com.d108.project.domain.board.BoardService;
import com.d108.project.domain.board.dto.BoardCreateDto;
import com.d108.project.domain.board.dto.BoardResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 전체 글 조회 (기본 주소)
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getBoards() {
        return ResponseEntity.ok(boardService.getBoards());
    }

    // 글 작성
    @PostMapping("/create")
    public ResponseEntity<Board> createBoard(@RequestBody BoardCreateDto boardCreateDto) {
        Board board = boardService.createBoard(boardCreateDto);
        return ResponseEntity.ok(board);
    }

    // 특정 글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> getBoardById(@PathVariable Long boardId) {
        BoardResponseDto boardResponseDto = boardService.getBoardById(boardId);
        return ResponseEntity.ok(boardResponseDto);
    }

    // 글 수정
    // @PutMapping("/{boardId}")

}
