package com.d108.project.domain.comment;

import com.d108.project.domain.comment.dto.CommentCreateDto;

// 댓글
public interface CommentService {
    // 생성
    Comment createComment(Long boardId, CommentCreateDto commentCreateDto);
    // 삭제
    void deleteComment(Long commentId);
}
