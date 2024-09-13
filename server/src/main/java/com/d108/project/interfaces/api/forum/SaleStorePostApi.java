package com.d108.project.interfaces.api.forum;

import com.d108.project.domain.forum.saleStorePost.dto.SaleStorePostCreateDto;
import com.d108.project.domain.forum.saleStorePost.dto.SaleStorePostResponseDto;
import com.d108.project.domain.forum.saleStorePost.dto.SaleStorePostUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface SaleStorePostApi {

    @PostMapping("/sale-posts")
    ResponseEntity<Void> createSalePost(@RequestBody SaleStorePostCreateDto saleStorePostCreateDto);

    @PutMapping("/sale-posts/{postId}")
    ResponseEntity<Void> updateSalePost(@PathVariable Long postId, @RequestParam Long memberId, @RequestBody SaleStorePostUpdateDto saleStorePostUpdateDto);

    @GetMapping("/sale-posts/{postId}")
    ResponseEntity<SaleStorePostResponseDto> getSalePostById(@PathVariable Long postId);

    @GetMapping("/sale-posts")
    ResponseEntity<List<SaleStorePostResponseDto>> getAllSalePosts();

    @DeleteMapping("/sale-posts/{postId}")
    ResponseEntity<Void> deleteSalePost(@PathVariable Long postId, @RequestParam Long memberId);
}
