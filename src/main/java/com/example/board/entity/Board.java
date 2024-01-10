package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // Article.classification <-> Board.Id와 일치
    // 나중에 Article의 Id순서와 title 등의 정보를 가져올거임
    @OneToMany(mappedBy = "classification")
    private List<Article> classifiedArticles;

    // 01-11-07:33 추가
    // 아래 부분 미 구현시, 양방향 호출시(toString) 서로를 무한으로 참조하여 StackOverFlow 현상 발생했음 (현재 해결 완)
    @Override
    public String toString() {
        // 'classifiedArticles' 필드는 toString()에서 제외
        return "Board{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
