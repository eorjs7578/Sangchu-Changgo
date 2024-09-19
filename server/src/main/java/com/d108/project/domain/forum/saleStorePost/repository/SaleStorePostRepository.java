package com.d108.project.domain.forum.saleStorePost.repository;

import com.d108.project.domain.forum.saleStorePost.entity.SaleStorePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleStorePostRepository extends JpaRepository<SaleStorePost, Long> {

    @Query("SELECT p.id FROM SaleStorePost p")  // JPQL 쿼리 추가
    List<Long> findAllSaleStorePostIds();
}
