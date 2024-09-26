package com.d108.project.domain.forum.board.dto;

// 최신 글 제목, 쓴 날짜, 총 게시물 개수
// Latest post title, date written, total number of posts
// dd-hh:mm
public record BoardResponseDto(Long id, String name, String latestPostTitle, String latestPostDate, Long postCount) {

}
