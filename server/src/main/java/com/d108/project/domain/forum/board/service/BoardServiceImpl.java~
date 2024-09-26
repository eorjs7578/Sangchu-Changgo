package com.d108.project.domain.forum.board.service;

import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.area.repository.AreaRepository;
import com.d108.project.domain.forum.board.dto.BoardRequestSearchDto;
import com.d108.project.domain.forum.board.dto.BoardResponseDto;
import com.d108.project.domain.forum.board.entity.Board;
import com.d108.project.domain.forum.board.repository.BoardRepository;
import com.d108.project.domain.forum.board.repository.BoardSearchRepository;
import com.d108.project.domain.franchise.entity.Franchise;
import com.d108.project.domain.franchise.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardSearchRepository boardSearchRepository;

    private final AreaRepository areaRepository;
    private final FranchiseRepository franchiseRepository;

    @Override
    public List<BoardResponseDto> getAllBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Board> allBoards = boardRepository.findAll(pageable);
        return allBoards.stream()
                .map(this::toDto)  // Board 엔티티를 BoardResponseDto로 변환
                .toList();
    }

    @Override
    public List<BoardResponseDto> getAllFranchiseBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Board> franchiseBoards = boardRepository.findByFranchiseIdIsNotNull(pageable); // 프랜차이즈 게시판 조회
        return franchiseBoards.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<BoardResponseDto> getAllAreaBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Board> areaBoards = boardRepository.findByAreaIdIsNotNull(pageable); // 지역 게시판 조회
        return areaBoards.stream()
                .map(this::toDto)
                .toList();
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

        return toDto(saleBoard);
    }

    @Override
    public List<BoardResponseDto> searchBoard(BoardRequestSearchDto boardRequestSearchDto) {
        // 검색 조건에 맞는 게시판 조회 로직
        String keyword = boardRequestSearchDto.keyword();
        List<Board> searchResults = boardSearchRepository.searchBoardsByKeyword(keyword);

        return searchResults.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<BoardResponseDto> searchFranchiseBoard(BoardRequestSearchDto boardRequestSearchDto) {
        // 검색 조건에 맞는 게시판 조회 로직
        String keyword = boardRequestSearchDto.keyword();
        List<Board> searchResults = boardSearchRepository.searchFranchiseBoardsByKeyword(keyword);

        return searchResults.stream()
                .map(this::toDto)
                .toList();
    }


    @Override
    public List<BoardResponseDto> searchAreaBoard(BoardRequestSearchDto boardRequestSearchDto) {
        // 검색 조건에 맞는 게시판 조회 로직
        String keyword = boardRequestSearchDto.keyword();
        List<Board> searchResults = boardSearchRepository.searchAreaBoardsByKeyword(keyword);

        return searchResults.stream()
                .map(this::toDto)
                .toList();
    }

    // Board -> BoardResponseDto로 변환하는 메서드
    private BoardResponseDto toDto(Board board) {
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

        return new BoardResponseDto(board.getId(), boardName);
    }
}
