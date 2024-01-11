package com.example.board.repo;

import com.example.board.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository를 상속받는 인터페이스
// JpaRepository의 기능을 사용해서 데이터베이스와 소통할 수 있다.
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByClassificationId(Long classificationId);

    List<Article> findByClassificationIdOrderByIdDesc(Long classificationId);
    List<Article> findAllByOrderByIdDesc();

}
