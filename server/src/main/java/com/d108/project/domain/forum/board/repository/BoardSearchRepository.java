package com.d108.project.domain.forum.board.repository;

import com.d108.project.domain.forum.board.entity.Board;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.d108.project.domain.area.entity.QArea.area;
import static com.d108.project.domain.forum.board.entity.QBoard.board;
import static com.d108.project.domain.franchise.entity.QFranchise.franchise;

@Repository
public class BoardSearchRepository {

    private final JPAQueryFactory queryFactory;

    public BoardSearchRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 재사용 가능한 검색 로직
    private List<Board> searchBoards(BooleanExpression condition, int limit) {
        return queryFactory.selectFrom(board)
                .leftJoin(board.franchise, franchise)
                .leftJoin(board.area, area)
                .where(condition)
                .limit(limit)
                .fetch();
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
}
