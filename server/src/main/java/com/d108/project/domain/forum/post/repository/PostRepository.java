package com.d108.project.domain.forum.post.repository;

import com.d108.project.domain.forum.board.entity.Board;
import com.d108.project.domain.forum.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT post.id FROM Post post")
    List<Long> findAllPostIds();

    @Query("SELECT post FROM Post post " +
            "WHERE post.board.franchise.id = :franchiseId")
    List<Post> findAllByFranchiseId(Long franchiseId);

    @Query("SELECT post FROM Post post " +
            "WHERE post.board.area.id = :areaId")
    List<Post> findAllByAreaId(Long areaId);

    @Query("SELECT post FROM Post post " +
            "WHERE post.board.franchise.id = :franchiseId " +
            "ORDER BY post.createdAt DESC")
    List<Post> findAllByFranchiseIdOrderByCreatedAtDesc(Long franchiseId);

    Optional<Post> findFirstByBoardOrderByCreatedAtDesc(Board board);
    long countByBoard(Board board);

    Page<Post> findByBoard(Pageable pageable, Board board);
}
