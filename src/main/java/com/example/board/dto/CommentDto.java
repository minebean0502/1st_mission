package com.example.board.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private String context;
    private String password;
}
