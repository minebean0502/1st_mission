package com.example.board.repo;

import com.example.board.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository를 상속받는 인터페이스
// JpaRepository의 기능을 사용해서 데이터베이스와 소통할 수 있다.
public interface ArticleRepository extends JpaRepository<Article, Long> {
    // # 기능 수정(제거) : 01-11-09:11
    // 아래건 이젠 안씀
    List<Article> findByClassificationId(Long classificationId);
    List<Article> findByClassificationIdOrderByIdDesc(Long classificationId);
    List<Article> findAllByOrderByIdDesc();
}