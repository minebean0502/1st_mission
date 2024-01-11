package com.example.board;

import com.example.board.dto.ArticleDto;
import com.example.board.entity.Article;
import com.example.board.entity.Board;
import com.example.board.repo.ArticleRepository;
import com.example.board.repo.BoardRepository;
import com.example.board.repo.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    // private Long nextId = 1L;
    // 데이터를 담기 위한 리스트

    // 밑에건 조만간 삭제함
    // private final List<ArticleDto> articleList = new ArrayList<>();

    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    /* # 기능 이전입니다 : 01-11
    // 사용자가 만든 게시글의 데이터를 받아서
    // 새로운 게시글 객체를 생성해 리스트에 저장하기
    public ArticleDto createArticle(
            String title,
            String content,
            String password) {
        ArticleDto newArticle = new ArticleDto(nextId, title, content, password);
        nextId++;
        articleList.add(newArticle);
        return newArticle;
    }
     */
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
        // 게시글에 게시판의 id를 할당한다
        article.setClassification(optionalBoard.orElse(null));

        // repository의 save 메서드를 호출한다.
        articleRepository.save(article);
    }

    // # 기능 수정함: 01-11
    public Article readArticle(Long id) {
        Optional<Article> optionalArticle
                = articleRepository.findById(id);
        // 실제 데이터가 있으면 해당 데이터를, 없으면 null을 반환한다
        return optionalArticle.orElse(null);
    }

    public List<Article> readArticleAll() {
        List<Article> articles = articleRepository.findAll();
        return articles;
    }

    // 특정 classification_id에 해당하는 게시글 목록 조회
    // -> articleRepo에도 기능 추가해줘야함
//    public List<Article> getArticleByClassId(Long classificationId) {
//        return articleRepository.findByClassificationId(classificationId);
//    }

    // 방금 추가된거
    public List<Article> getArticlesOrderByIdDesc() {
        // 최신 순으로 전체 게시글 목록 반환
        return articleRepository.findAllByOrderByIdDesc();
    }

    public List<Article> getArticlesByClassificationIdOrderByDesc(Long classificationId) {
        // 최신 순으로 정렬된 게시글 목록 반환
        return articleRepository.findByClassificationIdOrderByIdDesc(classificationId);
    }


    /* # 기능 이전입니다 : 01-11
    // 현재 등록된 모든 게시글들을 반환한다.
    public List<ArticleDto> readArticleAll() {
        return articleList;
    }

    // id를 받아서 하나의 게시판만 반환한다.
    public ArticleDto readArticle(Long id) {
        // articleList의 데이터를 하나씩 확인해서
        // getId가 id인 데이터를 반환하고
        for (ArticleDto articleDto: articleList) {
            if (articleDto.getId().equals(id)) {
                return articleDto;
            }
        }
        return null;
    }
    */

    /* # 기능 이전입니다: 01-11
    // 어떤 게시글의 정보를 바꿀건지를 나타내는 id
    // id값과 비밀번호 값이 일치하면 title과 content 바꿔줌
    public ArticleDto updateArticle(
            Long id,
            String title,
            String content,
            String password) {
        for (ArticleDto articleDto: articleList) {
            if (articleDto.getId().equals(id)
                    && articleDto.getPassword().equals(password)) {
                articleDto.setTitle(title);
                articleDto.setContent(content);
                return articleDto;
            }
        }
        return null;
    }
     */

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

    /* # 기능 이전입니다: 01-11
    // 어떤 게시글을 삭제할지 나타내는 service
    public boolean deleteArticle(Long id, String password) {
        int target = -1;
        for (int i = 0; i < articleList.size(); i++) {
            if (articleList.get(i).getId().equals(id)
                    && articleList.get(i).getPassword().equals(password)) {
                target = i;
                break;
            }
        }
        // id값, password 일치 이후 target이 바뀐다면
        if (target != -1) {
            // 게시글을 제거하고 true 반환
            articleList.remove(target);
            return true;
        }
        else return false;
    }
     */

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
