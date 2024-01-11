package com.example.board;


import com.example.board.entity.Article;
import com.example.board.entity.Board;
import com.example.board.repo.ArticleRepository;
import com.example.board.repo.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;

    public void create(
            String title,
            String content,
            String password,
            Long classificationId
    ) {
        // 주어진 정보로 새로운 Article 객체를 만든다
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setPassword(password);
        // (일치시킬) 게시판을 찾는다.
        Optional<Board> optionalBoard
                = boardRepository.findById(classificationId);
        // 게시글에 게시판의 id를 할당한다.
        article.setClassification(optionalBoard.orElse(null));

        // repository의 save 메서드를 호출한다.
        articleRepository.save(article);
    }

    public Article readArticle(Long id) {
        Optional<Article> optionalArticle
                = articleRepository.findById(id);
        // 실제 데이터가 있으면 해당 데이터를, 없으면 null을 반환한다
        return optionalArticle.orElse(null);
    }

    public List<Article> getArticlesOrderByIdDesc() {
        // 최신 순으로 전체 게시글 목록 반환
        return articleRepository.findAllByOrderByIdDesc();
    }

    public List<Article> getArticlesByClassificationIdOrderByDesc(Long classificationId) {
        // 최신 순으로 정렬된 게시글 목록 반환
        return articleRepository.findByClassificationIdOrderByIdDesc(classificationId);
    }


    public void update(
            // 수정할 데이터의 PK가 무엇인지
            Long id,
            // 수정할 데이터
            String title,
            String content,
            // 검증용 password
            String password
    ) {
        // 1. 업데이트 할 데이터를 찾고
        Article target = readArticle(id);
        // 2. 데이터의 내용들 중, id값과 입력한 password와 같다면 갱신하고
        if (target.getId().equals(id) && target.getPassword().equals(password)) {
            target.setTitle(title);
            target.setContent(content);
            // 3. repository에 저장한다
            articleRepository.save(target);
        } else {
            // 예외가 발생한다면
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void delete(
            Long id, // 수정해야 할 article의 PK
            String password // 수정할 때 일치하는지 검증해야 하는 password
            ) {
        Article article = readArticle(id);
        if (article != null && article.getPassword().equals(password)) {
            articleRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + id));
    }
}
