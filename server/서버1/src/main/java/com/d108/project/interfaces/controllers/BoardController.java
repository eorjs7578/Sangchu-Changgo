package com.d108.project.interfaces.controllers;

import com.d108.project.domain.board.Board;
import com.d108.project.domain.board.BoardService;
import com.d108.project.domain.board.dto.BoardCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Controller("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/create")
    public ResponseEntity<Board> createBoard(@RequestBody BoardCreateDto boardCreateDto) {
        Board board = boardService.createBoard(boardCreateDto);
        return ResponseEntity.ok(board);
    }
}
