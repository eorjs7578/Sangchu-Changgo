package com.d108.project.domain.forum.board.service;

import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.area.repository.AreaRepository;
import com.d108.project.domain.forum.board.dto.BoardRequestSearchDto;
import com.d108.project.domain.forum.board.dto.BoardResponseDto;
import com.d108.project.domain.forum.board.dto.PageDTO;
import com.d108.project.domain.forum.board.entity.Board;
import com.d108.project.domain.forum.board.repository.BoardRepository;
import com.d108.project.domain.forum.board.repository.BoardSearchRepository;
import com.d108.project.domain.forum.post.entity.Post;
import com.d108.project.domain.forum.post.repository.PostRepository;
import com.d108.project.domain.franchise.entity.Franchise;
import com.d108.project.domain.franchise.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardSearchRepository boardSearchRepository;

    private final AreaRepository areaRepository;
    private final FranchiseRepository franchiseRepository;
    private final PostRepository postRepository;

    private final RedisTemplate<String, Object> BoardRedisTemplate;


    // TODO: 게시판 검색이 아니라 상권이나 프랜차이즈로 검색을 해주고 게시판 클릭시 없으면 만들어주는 것으로 구현하는게 좋을듯  
    @Override
    public Page<BoardResponseDto> getAllBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Board> allBoards = boardRepository.findAll(pageable);

        return getBoardResponseDtos(allBoards);

    }

    //프랜차이즈 조회
    @Override
    public Page<BoardResponseDto> getAllFranchiseBoards(int page, int size) {
        // 페이지 요청 객체 생성
        Pageable pageable = PageRequest.of(page, size);
        String key = "franchise_page_"+page+"_"+size;

        // Redis에서 조회
        PageDTO<BoardResponseDto> franchiseBoardsDTO = (PageDTO<BoardResponseDto>) BoardRedisTemplate.opsForValue().get(key);
        Page<BoardResponseDto> franchiseBoards = null;

        // Redis에 값이 없으면 DB에서 조회
        if (franchiseBoardsDTO == null) {
            log.info("db조회");
            System.out.println("db");
            Page<Board> boards = boardRepository.findByFranchiseIdIsNotNull(pageable);
            franchiseBoards = getBoardResponseDtos(boards);

            if (franchiseBoards != null) {
                // Page를 PageDTO로 변환하여 Redis에 저장
                PageDTO<BoardResponseDto> pageDTO = new PageDTO<>(franchiseBoards);
                BoardRedisTemplate.opsForValue().set(key, pageDTO);
            }
        } else {
            log.info("redis!");
            // Redis에서 가져온 PageDTO를 Page로 변환
            franchiseBoards = new PageImpl<>(
                    franchiseBoardsDTO.getContent(),
                    PageRequest.of(franchiseBoardsDTO.getPageNumber(), franchiseBoardsDTO.getPageSize()),
                    franchiseBoardsDTO.getTotalElements()
            );
        }

        System.out.println(franchiseBoards);
        // 각 Board에 대해 최신 글과 총 게시물 개수를 가져와 DTO로 변환
        return franchiseBoards;
    }

    @Override
    public Page<BoardResponseDto> getAllAreaBoards(int page, int size) {
        // 페이지 요청 객체 생성
        Pageable pageable = PageRequest.of(page, size);
        String key = "area_page_"+page+"_"+size;

        // Redis에서 조회
        PageDTO<BoardResponseDto> areaBoardsDTO = (PageDTO<BoardResponseDto>) BoardRedisTemplate.opsForValue().get(key);
        Page<BoardResponseDto> areaBoards = null;

        // Redis에 값이 없으면 DB에서 조회
        if (areaBoardsDTO == null) {
            log.info("db조회");
            System.out.println("db");
            Page<Board> boards = boardRepository.findByAreaIdIsNotNull(pageable);
            areaBoards = getBoardResponseDtos(boards);

            if (areaBoards != null) {
                // Page를 PageDTO로 변환하여 Redis에 저장
                PageDTO<BoardResponseDto> pageDTO = new PageDTO<>(areaBoards);
                BoardRedisTemplate.opsForValue().set(key, pageDTO);
            }
        } else {
            log.info("area redis!");
            // Redis에서 가져온 PageDTO를 Page로 변환
            areaBoards = new PageImpl<>(
                    areaBoardsDTO.getContent(),
                    PageRequest.of(areaBoardsDTO.getPageNumber(), areaBoardsDTO.getPageSize()),
                    areaBoardsDTO.getTotalElements()
            );
        }

        return areaBoards;
    }

    @Override
    public BoardResponseDto getSaleBoard() {
        // 판매 관련 게시판 조회 로직
        Board saleBoard = boardRepository.findSaleBoard()
                .orElseGet(() -> {
                    Board newBoard = new Board();
                    // 필요한 초기화 코드가 있으면 추가
                    // 저장된 객체는 saleBoard 변수에 할당됩니다.
                    return boardRepository.save(newBoard);
                });

        return getBoardResponseDto(saleBoard);
    }

    @Override
    public Page<BoardResponseDto> searchBoard(BoardRequestSearchDto boardRequestSearchDto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // 검색 조건에 맞는 게시판 조회 로직
        String keyword = boardRequestSearchDto.keyword();
        Page<Board> searchResults = boardSearchRepository.searchBoardsByKeyword(keyword, pageable);

        return getBoardResponseDtos(searchResults);
    }

    @Override
    public Page<BoardResponseDto> searchFranchiseBoard(BoardRequestSearchDto boardRequestSearchDto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // 검색 조건에 맞는 게시판 조회 로직
        String keyword = boardRequestSearchDto.keyword();

//        Page<Board> searchResults = boardRepository.searchFranchiseBoardsByKeyword(keyword, pageable);
        // 기존
        Page<Board> searchResults = boardSearchRepository.searchFranchiseBoardsByKeyword(keyword, pageable);

        return getBoardResponseDtos(searchResults);
    }

    @Override
    public Page<BoardResponseDto> searchAreaBoard(BoardRequestSearchDto boardRequestSearchDto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // 검색 조건에 맞는 게시판 조회 로직
        String keyword = boardRequestSearchDto.keyword();
        Page<Board> searchResults = boardSearchRepository.searchAreaBoardsByKeyword(keyword, pageable);

        return getBoardResponseDtos(searchResults);
    }


    @Override
    public List<BoardResponseDto> getPopularAreaBoard() {
        List<Board> popularAreaBoards = boardSearchRepository.getPopularAreaBoards();

        return getBoardResponseDtos(popularAreaBoards);
    }

    @Override
    public List<BoardResponseDto> getPopularFranchiseBoard() {
        List<Board> popularFranchiseBoards = boardSearchRepository.getPopularFranchiseBoards();

        return getBoardResponseDtos(popularFranchiseBoards);
    }


    // Board -> BoardResponseDto로 변환하는 메서드
    private BoardResponseDto toDto(Board board, Optional<Post> latestPost, Long postCount) {
        String boardName = "상권 매물";

        if (board.getFranchise() != null) {
            Franchise franchise = franchiseRepository.findById(board.getFranchise().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Franchise not found with id: " + board.getFranchise().getId()));
            boardName = franchise.getName();  // 프랜차이즈 이름 사용
        } else if (board.getArea() != null) {
            Area area = areaRepository.findById(board.getArea().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Area not found with id: " + board.getArea().getId()));
            boardName = area.getAreaName();  // 지역 이름 사용
        }

        if (latestPost.isEmpty()) {
            return new BoardResponseDto(board.getId(), boardName, null, null, postCount);
        }

        Post post = latestPost.get();

        LocalDateTime createdAt = post.getCreatedAt();
        String latestPostDate = createdAt.getDayOfMonth() + "-" + createdAt.getHour() + ":" + createdAt.getMinute();

        return new BoardResponseDto(board.getId(), boardName, post.getTitle(), latestPostDate, postCount);
    }

    // Collection<Board>를 처리하는 메서드
    private List<BoardResponseDto> getBoardResponseDtos(Collection<Board> boards) {
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();

        for (Board board : boards) {
            Optional<Post> latestPost = postRepository.findFirstByBoardOrderByCreatedAtDesc(board);
            Long postCount = postRepository.countByBoard(board);
            boardResponseDtoList.add(toDto(board, latestPost, postCount));
        }

        return boardResponseDtoList;
    }

    // Page<Board>를 처리하는 메서드 (Collection<Board>로 변환 후 처리)
    private Page<BoardResponseDto> getBoardResponseDtos(Page<Board> boards) {
        List<BoardResponseDto> boardResponseDtoList = getBoardResponseDtos(boards.getContent()); // Page에서 리스트로 변환

        // Page<BoardResponseDto>로 변환
        return new PageImpl<>(boardResponseDtoList, boards.getPageable(), boards.getTotalElements());
    }
    // 단일 Board를 처리하는 메서드 (편의 메서드)
    private BoardResponseDto getBoardResponseDto(Board board) {
        // 단일 Board를 처리하려면, 그 Board를 리스트로 감싸서 메서드에 전달할 수 있습니다.
        //Collections.singletonList(board)를 이용해 단일 Board를 크기 1의 리스트로 감싸서 getBoardResponseDtos 메서드에 전달
        return getBoardResponseDtos(Collections.singletonList(board)).get(0);
    }

}
