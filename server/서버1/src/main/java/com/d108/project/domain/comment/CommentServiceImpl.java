package com.d108.project.domain.comment;

import com.d108.project.domain.board.Board;
import com.d108.project.domain.board.BoardRepository;
import com.d108.project.domain.comment.dto.CommentCreateDto;
import com.d108.project.domain.user.User;
import com.d108.project.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, BoardRepository boardRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    // 댓글 생성
    @Override
    public Comment createComment(CommentCreateDto commentCreateDto) {
        Board board = boardRepository.findById(commentCreateDto.getBoardId())
                .orElseThrow(() -> new RuntimeException("게시글 번호를 찾을 수 없음"));

        User user = userRepository.findById(commentCreateDto.getUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없음"));

        Comment comment = new Comment();
        comment.setBoardId(commentCreateDto.getBoardId());
        comment.setUserId(commentCreateDto.getUserId());
        comment.setContent(commentCreateDto.getContent());
        return commentRepository.save(comment);
    }


    // 댓글 삭제
    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
