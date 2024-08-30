package com.d108.project.domain.board;


import com.d108.project.domain.board.dto.BoardCreateDto;
import com.d108.project.domain.user.User;
import com.d108.project.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    @Autowired
    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public Board createBoard(BoardCreateDto boardCreateDto) {
        User user = userRepository.findById(boardCreateDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("아이디 오류"));

        Board board = Board.builder()
                .boardTitle(boardCreateDto.getBoardTitle())
                .boardDescription(boardCreateDto.getBoardDescription())
                .user(user)
                .boardView(0L)
                .build();

        // 저장
        return boardRepository.save(board);
    }
}
