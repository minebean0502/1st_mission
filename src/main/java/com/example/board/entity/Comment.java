package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Comment {
    @Id // 이 속성(필드)가 테이블의 PK(Identity)임을 나타내는 어노테이션
    // 이 속성의 컬럼 데이터는 데이터베이스에서 자동으로 부여하는 값 및 어떻게 부여할지를 정의하는 어노테이션
    // 쉽게말해 AUTOINCREMENT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String context;
    private String password;

    // 댓글(다수) -> 게시글(단일) 매칭 시키는 관계
    // Comment.articleNum <-> article.Id와 일치
    @ManyToOne
    private Article articleNum;
}
