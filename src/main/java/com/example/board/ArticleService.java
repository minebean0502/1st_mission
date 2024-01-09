package com.example.board;

import com.example.board.dto.ArticleDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    private Long nextId = 1L;
    // 데이터를 담기 위한 리스트
    private final List<ArticleDto> articleList = new ArrayList<>();

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
}
