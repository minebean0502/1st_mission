package com.example.board;

import com.example.board.entity.Board;
import com.example.board.repo.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    // 전체 board들의 정보(Long id, String classification, String homepage) 읽기
    public List<Board> readBoardAll() {
        return boardRepository.findAll();
    }

    public Board getBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }
}
