package com.example.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    private CommentService service;
    public CommentController(CommentService service) {
        this.service = service;
    }
    // 1. 댓글 기능은 read/{id}에서 구현함
    // 2. 작성하는 dto에는 id와 content
//    @PostMapping("/read/{id}/comment")
//    public String createComment(Long id, String title, String password) {
//
//    }
}
