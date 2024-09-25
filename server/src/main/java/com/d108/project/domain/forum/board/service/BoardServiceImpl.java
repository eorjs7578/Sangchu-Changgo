package com.d108.project.domain.forum.board.service;

import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.area.repository.AreaRepository;
import com.d108.project.domain.forum.board.dto.BoardRequestSearchDto;
import com.d108.project.domain.forum.board.dto.BoardResponseDto;
import com.d108.project.domain.forum.board.entity.Board;
import com.d108.project.domain.forum.board.repository.BoardRepository;
import com.d108.project.domain.franchise.entity.Franchise;
import com.d108.project.domain.franchise.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final AreaRepository areaRepository;
    private final FranchiseRepository franchiseRepository;

    @Override
    public List<BoardResponseDto> getAllBoards() {
        List<Board> allBoards = boardRepository.findAll();
        return allBoards.stream()
                .map(this::toDto)  // Board 엔티티를 BoardResponseDto로 변환
                .collect(Collectors.toList());
    }
    @Override
    public List<BoardResponseDto> getAllFranchiseBoards() {
        List<Board> franchiseBoards = boardRepository.findByFranchiseIdIsNotNull(); // 프랜차이즈 게시판 조회
        return franchiseBoards.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardResponseDto> getAllAreaBoards() {
        List<Board> areaBoards = boardRepository.findByAreaIdIsNotNull(); // 지역 게시판 조회
        return areaBoards.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BoardResponseDto getSaleBoard() {
        // 판매 관련 게시판 조회 로직
        Board saleBoard = boardRepository.findSaleBoard()
                .orElse( new Board());
        return saleBoard != null ? toDto(saleBoard) : null;
    }

    @Override
    public BoardResponseDto searchBoard(BoardRequestSearchDto boardRequestSearchDto) {
        // 검색 조건에 맞는 게시판 조회 로직
        List<Board> searchResults = boardRepository.searchBoard(boardRequestSearchDto);

        return searchResults.isEmpty() ? null : toDto(searchResults.get(0));
    }

    // Board -> BoardResponseDto로 변환하는 메서드
    private BoardResponseDto toDto(Board board) {
        String boardName = "상권 매물";

        if (board.getFranchiseId() != null) {
            Franchise franchise = franchiseRepository.findById(board.getFranchiseId())
                    .orElseThrow(() -> new IllegalArgumentException("Franchise not found with id: " + board.getFranchiseId()));
            boardName = franchise.getName();  // 프랜차이즈 이름 사용
        } else if (board.getAreaId() != null) {
            Area area = areaRepository.findById(board.getAreaId())
                    .orElseThrow(() -> new IllegalArgumentException("Area not found with id: " + board.getAreaId()));
            boardName = area.getAreaName();  // 지역 이름 사용
        }

        return new BoardResponseDto(board.getId(), boardName);
    }
}
