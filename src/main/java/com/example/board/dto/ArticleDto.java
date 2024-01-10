package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
// @AllArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private String password;
}
