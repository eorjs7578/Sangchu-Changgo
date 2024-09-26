package com.d108.project.domain.forum.board.repository;

import com.d108.project.domain.forum.board.entity.Board;
import com.d108.project.domain.forum.board.entity.QBoard;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
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


    // 키워드를 사용하여 상권 및 프랜차이즈 검색
    public List<Board> searchBoardsByKeyword(String keyword) {
        BooleanExpression condition = franchise.name.containsIgnoreCase(keyword)
                .or(area.areaName.containsIgnoreCase(keyword));
        return searchBoards(condition, 10);
    }

    // 지역 이름으로 검색
    public List<Board> searchAreaBoardsByKeyword(String keyword) {
        BooleanExpression condition = area.areaName.containsIgnoreCase(keyword);
        return searchBoards(condition, 10);
    }

    // 프랜차이즈 이름으로 검색
    public List<Board> searchFranchiseBoardsByKeyword(String keyword) {
        BooleanExpression condition = franchise.name.containsIgnoreCase(keyword);
        return searchBoards(condition, 10);
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

    private List<Board> searchBoards(BooleanExpression condition, int limit) {
        return queryFactory.selectFrom(board)
                .leftJoin(board.franchise, franchise)
                .leftJoin(board.area, area)
                .where(condition)
                .limit(limit)
                .fetch();
    }
}
