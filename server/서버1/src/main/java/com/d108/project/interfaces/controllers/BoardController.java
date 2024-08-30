package com.d108.project.interfaces.controllers;

import com.d108.project.domain.board.Board;
import com.d108.project.domain.board.BoardService;
import com.d108.project.domain.board.dto.BoardCreateDto;
import com.d108.project.domain.board.dto.BoardResponseDto;
import com.d108.project.domain.board.dto.BoardUpdateDto;
import com.d108.project.domain.comment.Comment;
import com.d108.project.domain.comment.CommentService;
import com.d108.project.domain.comment.dto.CommentCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @Autowired
    public BoardController(BoardService boardService, CommentService commentService) {
        this.boardService = boardService;
        this.commentService = commentService;
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
    @PutMapping("/{boardId}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long boardId, @RequestBody BoardUpdateDto boardUpdateDto) {
        Board board = boardService.updateBoardById(boardId, boardUpdateDto);
        return ResponseEntity.ok(board);
    }

    // 글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoardById(boardId);
        return ResponseEntity.noContent().build();
    }



    // 댓글 생성
    @PostMapping("/{boardId}/comment")
    public ResponseEntity<Comment> createComment(@PathVariable Long boardId, @RequestBody CommentCreateDto commentCreateDto) {

        Comment comment = commentService.createComment(commentCreateDto);
        return ResponseEntity.ok(comment);
    }

    // 댓글 삭제
    @DeleteMapping("/{boardId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
