package com.example.board.repo;

import com.example.board.entity.Article;
import com.example.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
