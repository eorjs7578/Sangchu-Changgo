package com.d108.project.interfaces.controllers;

import com.d108.project.domain.board.Board;
import com.d108.project.domain.board.BoardServiceImpl;
import com.d108.project.domain.board.dto.BoardCreateDto;
import com.d108.project.domain.board.dto.BoardResponseDto;
import com.d108.project.domain.board.dto.BoardUpdateDto;
import com.d108.project.domain.comment.Comment;
import com.d108.project.domain.comment.CommentServiceImpl;
import com.d108.project.domain.comment.dto.CommentCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardServiceImpl boardService;
    private final CommentServiceImpl commentService;

    @Autowired
    public BoardController(BoardServiceImpl boardService, CommentServiceImpl commentService) {
        this.boardService = boardService;
        this.commentService = commentService;
    }

    // 전체 글 조회 (기본 주소)
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getBoards() {
        return ResponseEntity.ok(boardService.getBoards());
    }

    // 글 작성
    // 작성 후에 작성 결과를 보여줄 필요 없음
    @PostMapping
    public ResponseEntity<String> createBoard(@RequestBody BoardCreateDto boardCreateDto) {
        boardService.createBoard(boardCreateDto);
        return ResponseEntity.ok("게시글 작성 완료");
    }


    // 특정 글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> getBoardById(@PathVariable Long boardId) {
        BoardResponseDto boardResponseDto = boardService.getBoardById(boardId);
        return ResponseEntity.ok(boardResponseDto);
    }

    // 글 수정
    // 수정 후에 수정 결과를 보여줄 필요 없음
    @PutMapping("/{boardId}")
    public ResponseEntity<String> updateBoard(@PathVariable Long boardId, @RequestBody BoardUpdateDto boardUpdateDto) {
        Board board = boardService.updateBoardById(boardId, boardUpdateDto);
        return ResponseEntity.ok("게시글 수정 완료");
    }

    // 글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoardById(boardId);
        return ResponseEntity.noContent().build();
    }

    // 댓글 생성
    // 생성 후에 생성 결과를 보여줄 필요 없음
    @PostMapping("/{boardId}/comment")
    public ResponseEntity<String> createComment(@PathVariable Long boardId, @RequestBody CommentCreateDto commentCreateDto) {
        commentService.createComment(boardId, commentCreateDto);
        return ResponseEntity.ok("댓글 작성 완료");
    }

    // 댓글 삭제
    @DeleteMapping("/{boardId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
