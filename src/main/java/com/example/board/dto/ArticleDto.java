package com.example.board.dto;

import lombok.Data;


@Data
public class ArticleDto {
    private Long id;
    private String title;
    private String context;
    private Integer password;
}
