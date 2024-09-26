package com.d108.project.domain.forum.saleStorePost.service;

import com.d108.project.cache.redis.RedisUtil;
import com.d108.project.domain.evaluation.areaEvaluation.service.AreaEvaluationService;
import com.d108.project.domain.forum.saleStorePost.dto.SaleStorePostCreateDto;
import com.d108.project.domain.forum.saleStorePost.dto.SaleStorePostResponseDto;
import com.d108.project.domain.forum.saleStorePost.dto.SaleStorePostTypeListDto;
import com.d108.project.domain.forum.saleStorePost.dto.SaleStorePostUpdateDto;
import com.d108.project.domain.forum.saleStorePost.entity.SaleStorePost;
import com.d108.project.domain.forum.saleStorePost.repository.SaleStorePostRepository;
import com.d108.project.domain.forum.board.entity.Board;
import com.d108.project.domain.forum.board.repository.BoardRepository;
import com.d108.project.domain.global.enums.*;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleStorePostServiceImpl implements SaleStorePostService {

    private final RedisUtil redisUtil;
    private final SaleStorePostRepository saleStorePostRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    private final AreaEvaluationService areaEvaluationService;
    private static final String REDIS_PREFIX = "salestorepost:viewCount:";

    // 매출 게시글 생성
    @Override
    public Long createSaleStorePost(SaleStorePostCreateDto saleStorePostCreateDto) {
        Board board = boardRepository.findById(saleStorePostCreateDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판입니다."));

        Member member = memberRepository.findById(saleStorePostCreateDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 수동 생성자 사용하여 SaleStorePost 객체 생성
        SaleStorePost saleStorePost = new SaleStorePost(
                board,
                member,
                saleStorePostCreateDto.getTitle(),
                saleStorePostCreateDto.getContent(),
                saleStorePostCreateDto.getStartupPrice(),
                saleStorePostCreateDto.getRentalPrice(),
                saleStorePostCreateDto.getRevenue(),
                saleStorePostCreateDto.getSize(),
                AgeGroup.fromDescription(saleStorePostCreateDto.getAgeGroup()),
                FootTraffic.fromDescription(saleStorePostCreateDto.getFootTraffic()),
                Atmosphere.fromDescription(saleStorePostCreateDto.getAtmosphere()),
                NearbyPrice.fromDescription(saleStorePostCreateDto.getNearbyPrice()),
                FranchiseType.fromDescription(saleStorePostCreateDto.getFranchiseType()),
                saleStorePostCreateDto.getDesiredSalePrice()
        );

        saleStorePostRepository.save(saleStorePost);

        return saleStorePost.getId();
    }

    // 전체 매출 게시글 조회
    @Override
    public List<SaleStorePostResponseDto> getAllSaleStorePosts() {
        List<SaleStorePost> saleStorePosts = saleStorePostRepository.findAll();

        return saleStorePosts.stream()
                .map(post -> {
                    SaleStorePostResponseDto responseDto = SaleStorePostResponseDto.from(post);
                    responseDto.setView(getViewCountById(post.getId()));
                    return responseDto;
                })
                .collect(Collectors.toList());
    }

    // 단일 매출 게시글 조회
    @Override
    public SaleStorePostResponseDto getSaleStorePostById(Long postId) {
        SaleStorePost saleStorePost = saleStorePostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 번호입니다."));

        Long view = incrementViewCountById(postId);

        SaleStorePostResponseDto responseDto = SaleStorePostResponseDto.from(saleStorePost);
        responseDto.setView(view);

        return responseDto;
    }

    // 매출 게시글 수정
    @Override
    public void updateSaleStorePostById(Long postId, Long memberId, SaleStorePostUpdateDto saleStorePostUpdateDto) {
        SaleStorePost saleStorePost = saleStorePostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 번호입니다."));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!member.equals(saleStorePost.getMember())) {
            throw new AccessDeniedException("본인의 글만 수정할 수 있습니다.");
        }

        saleStorePost.setTitle(saleStorePostUpdateDto.getTitle());
        saleStorePost.setContent(saleStorePostUpdateDto.getContent());
        saleStorePost.setRevenue(saleStorePostUpdateDto.getRevenue());
        saleStorePost.setStartupPrice(saleStorePostUpdateDto.getStartupPrice());
        saleStorePost.setRentalPrice(saleStorePostUpdateDto.getRentalPrice());
        saleStorePost.setSize(saleStorePostUpdateDto.getSize());
        saleStorePost.setFranchiseType(FranchiseType.fromDescription(saleStorePostUpdateDto.getFranchiseType()));
        saleStorePost.setAgeGroup(AgeGroup.fromDescription(saleStorePostUpdateDto.getAgeGroup()));
        saleStorePost.setFootTraffic(FootTraffic.fromDescription(saleStorePostUpdateDto.getFootTraffic()));
        saleStorePost.setAtmosphere(Atmosphere.fromDescription(saleStorePostUpdateDto.getAtmosphere()));
        saleStorePost.setNearbyPrice(NearbyPrice.fromDescription(saleStorePostUpdateDto.getNearbyPrice()));
        saleStorePost.setDesiredSalePrice(saleStorePostUpdateDto.getDesiredSalePrice());

        saleStorePostRepository.save(saleStorePost);
    }

    // 매출 게시글 삭제
    @Override
    public void deleteSaleStorePostById(Long postId, Long memberId) {
        SaleStorePost saleStorePost = saleStorePostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!member.equals(saleStorePost.getMember())) {
            throw new AccessDeniedException("본인의 글만 삭제할 수 있습니다.");
        }

        redisUtil.deleteData(REDIS_PREFIX + postId);
        saleStorePostRepository.deleteById(postId);
    }

    // 조회수 증가
    private Long incrementViewCountById(Long postId) {
        String redisKey = REDIS_PREFIX + postId;
        return redisUtil.incrementView(redisKey, RedisUtil.REDIS_VIEW_EXPIRE);
    }

    // 조회수 조회
    private Long getViewCountById(Long postId) {
        String redisKey = REDIS_PREFIX + postId;
        String viewCountStr = redisUtil.getData(redisKey);

        if (viewCountStr == null) {
            SaleStorePost saleStorePost = saleStorePostRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
            viewCountStr = saleStorePost.getView().toString();
            redisUtil.setDataExpire(redisKey, viewCountStr, RedisUtil.REDIS_VIEW_EXPIRE);
        }

        return (viewCountStr == null) ? 0 : Long.parseLong(viewCountStr);
    }

    // 조회수 DB 동기화
    @Override
    @Transactional
    public void syncCountToDatabase(Long postId) {
        String redisKey = REDIS_PREFIX + postId;

        SaleStorePost saleStorePost = saleStorePostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        saleStorePost.setView(getViewCountById(postId));
        saleStorePostRepository.save(saleStorePost);

        redisUtil.deleteData(redisKey);
    }

    // 모든 매출 게시글 ID 조회
    @Override
    public List<Long> getAllSaleStorePostIds() {
        return saleStorePostRepository.findAllSaleStorePostIds();
    }

    @Override
    public SaleStorePostTypeListDto getSaleStorePostTypeList() {
        return new SaleStorePostTypeListDto(areaEvaluationService.getEvaluationsByAreaTypeList(), FranchiseType.getAllDescriptions());
    }
}
