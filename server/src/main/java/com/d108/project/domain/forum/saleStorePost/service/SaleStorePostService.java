package com.d108.project.domain.forum.saleStorePost.service;

import com.d108.project.domain.forum.saleStorePost.dto.SaleStorePostCreateDto;
import com.d108.project.domain.forum.saleStorePost.dto.SaleStorePostUpdateDto;
import com.d108.project.domain.forum.saleStorePost.dto.SaleStorePostResponseDto;

import java.util.List;

public interface SaleStorePostService {

    // 매물 게시글 생성
    Long createSaleStorePost(SaleStorePostCreateDto saleStorePostCreateDto);

    // 모든 매물 게시글 조회
    List<SaleStorePostResponseDto> getAllSaleStorePosts();

    // 특정 매물 게시글 조회 (ID로)
    SaleStorePostResponseDto getSaleStorePostById(Long postId);

    // 매물 게시글 수정 (ID와 작성자 확인)
    void updateSaleStorePostById(Long postId, Long memberId, SaleStorePostUpdateDto saleStorePostUpdateDto);

    // 매물 게시글 삭제 (ID와 작성자 확인)
    void deleteSaleStorePostById(Long postId, Long memberId);

    // 조회수 동기화
    void syncCountToDatabase(Long postId);

    // 모든 매물 게시글 ID 조회
    List<Long> getAllSaleStorePostIds();
}
