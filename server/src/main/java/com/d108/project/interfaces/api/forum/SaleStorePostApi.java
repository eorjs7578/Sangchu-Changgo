package com.d108.project.interfaces.api.forum;

import com.d108.project.domain.forum.saleStorePost.dto.SaleStorePostCreateDto;
import com.d108.project.domain.forum.saleStorePost.dto.SaleStorePostResponseDto;
import com.d108.project.domain.forum.saleStorePost.dto.SaleStorePostTypeListDto;
import com.d108.project.domain.forum.saleStorePost.dto.SaleStorePostUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/posts/sale-store")
public interface SaleStorePostApi {

    @PostMapping
    ResponseEntity<Void> createSalePost(@RequestBody SaleStorePostCreateDto saleStorePostCreateDto);

    @PutMapping("/{postId}")
    ResponseEntity<Void> updateSalePost(@PathVariable Long postId, @RequestParam Long memberId, @RequestBody SaleStorePostUpdateDto saleStorePostUpdateDto);

    @GetMapping("/{postId}")
    ResponseEntity<SaleStorePostResponseDto> getSalePostById(@PathVariable Long postId);

    @GetMapping
    ResponseEntity<List<SaleStorePostResponseDto>> getAllSalePosts();

    @DeleteMapping("/{postId}")
    ResponseEntity<Void> deleteSalePost(@PathVariable Long postId, @RequestParam Long memberId);

    @GetMapping("/types")
    ResponseEntity<SaleStorePostTypeListDto> getSaleStorePostTypeList();
}
