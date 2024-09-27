package com.d108.project.domain.forum.board.repository;

import com.d108.project.domain.forum.board.entity.Board;
import com.d108.project.domain.forum.board.entity.QBoard;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.d108.project.domain.area.entity.QArea.area;
import static com.d108.project.domain.forum.board.entity.QBoard.board;
import static com.d108.project.domain.forum.post.entity.QPost.post;
import static com.d108.project.domain.franchise.entity.QFranchise.franchise;

@Repository
public class BoardSearchRepository {

    private final JPAQueryFactory queryFactory;

    public BoardSearchRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    // 키워드를 사용하여 상권 및 프랜차이즈 검색 (페이징 추가)
    public Page<Board> searchBoardsByKeyword(String keyword, Pageable pageable) {
        BooleanExpression condition = franchise.name.containsIgnoreCase(keyword)
                .or(area.areaName.containsIgnoreCase(keyword));
        return searchBoards(condition, pageable);
    }

    // 지역 이름으로 검색 (페이징 추가)
    public Page<Board> searchAreaBoardsByKeyword(String keyword, Pageable pageable) {
        BooleanExpression condition = area.areaName.containsIgnoreCase(keyword);
        return searchBoards(condition, pageable);
    }

    // 프랜차이즈 이름으로 검색 (페이징 추가)
    public Page<Board> searchFranchiseBoardsByKeyword(String keyword, Pageable pageable) {
        BooleanExpression condition = franchise.name.containsIgnoreCase(keyword);
        return searchBoards(condition, pageable);
    }


    public List<Board> getPopularFranchiseBoards() {
        // 1주일 전 시간을 구함
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);

        NumberExpression<Long> postCount = post.countDistinct();

        return queryFactory.selectFrom(board)
                .leftJoin(board.franchise, franchise)
                .leftJoin(board.posts, post)
                .where(board.franchise.isNotNull())
                .where(post.createdAt.after(oneWeekAgo))  // 최근 1주일 동안의 게시글
                .groupBy(board)  // 게시판별 그룹화
                .orderBy(postCount.desc())  // 게시글 수 많은 순서로 정렬
                .limit(10)
                .fetch();
    }

    public List<Board> getPopularAreaBoards() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);

        NumberExpression<Long> postCount = post.countDistinct();

        return queryFactory.selectFrom(board)
                .leftJoin(board.posts, post)
                .where(board.area.isNotNull())
                .where(post.createdAt.after(oneWeekAgo))  // 최근 1주일 동안의 게시글
                .groupBy(board)  // 게시판별 그룹화
                .orderBy(postCount.desc())  // 게시글 수 많은 순서로 정렬
                .limit(10)
                .fetch();
    }

    // 검색 로직 (페이징 추가)
    private Page<Board> searchBoards(BooleanExpression condition, Pageable pageable) {
        List<Board> results = queryFactory.selectFrom(board)
                .leftJoin(board.franchise, franchise)
                .leftJoin(board.area, area)
                .where(condition)
                .offset(pageable.getOffset())  // 페이징 시작 지점
                .limit(pageable.getPageSize())  // 페이지 크기
                .fetch();

        long total = queryFactory.selectFrom(board)
                .leftJoin(board.franchise, franchise)
                .leftJoin(board.area, area)
                .where(condition)
                .fetch().size();

        return new PageImpl<>(results, pageable, total);
    }
}
