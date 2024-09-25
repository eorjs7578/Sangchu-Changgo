package com.d108.project.domain.forum.board.service;

import com.d108.project.domain.area.repository.AreaRepository;
import com.d108.project.domain.forum.board.dto.BoardRequestSearchDto;
import com.d108.project.domain.forum.board.dto.BoardResponseDto;
import com.d108.project.domain.forum.board.entity.Board;
import com.d108.project.domain.forum.board.repository.BoardRepository;
import com.d108.project.domain.franchise.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final AreaRepository areaRepository;
    private final FranchiseRepository franchiseRepository;

    @Override
    public List<BoardResponseDto> getAllBoards() {

    }

    @Override
    public List<BoardResponseDto> getAllFranchiseBoards() {

    }

    @Override
    public List<BoardResponseDto> getAllAreaBoards() {
        return List.of();
    }

    @Override
    public BoardResponseDto getSaleBoard() {
        return null;
    }

    @Override
    public BoardResponseDto searchBoard(BoardRequestSearchDto boardRequestSearchDto) {
        return null;
    }
}
