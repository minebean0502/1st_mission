package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String password;

    // 게시글(다수) -> 게시판(단일) 매칭 시키는 관계
    // Article.classification <-> Board.Id와 일치
    @ManyToOne
    private Board classification;

    // Comment.articleNum <-> article.Id와 일치
    // 나중에 Comment에서 필요한 context랑 password 가져올거임
    @OneToMany(mappedBy = "articleNum", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> attachedComment;

    // 01-11-07:33 추가
    // 아래 부분 미 구현시, 양방향 호출시(toString) 서로를 무한으로 참조하여 StackOverFlow 현상 발생했음 (현재 해결 완)
    @Override
    public String toString() {
        // 'classification' 필드는 toString()에서 제외
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
