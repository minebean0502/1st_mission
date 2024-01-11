package com.example.board;

import com.example.board.entity.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommentController {
    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/comment/create")
    public String createComment(
            @RequestParam("context")
            String context,
            @RequestParam("password")
            String password,
            @RequestParam("articleId")
            Long articleNumId
    ) {
        commentService.createComment(context, password, articleNumId);
        return String.format("redirect:/read/" + articleNumId);
    }

    @PostMapping("/comments/delete")
    public String deleteComment(
            @RequestParam("commentId")
            Long commentId,
            @RequestParam("password")
            String password,
            @RequestParam("articleId")
            Long articleId, // 게시글 ID 추가
            RedirectAttributes redirectAttributes) {
        boolean isDeleted = commentService.deleteComment(commentId, password);
        // 비밀번호가 일치하지 않으면 오류 메세지를 출력함
        if (!isDeleted) {
            redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
        }
        // 성공하든 실패하는 해당 게시글의 상세 조회 페이지로 리다이렉트
        return "redirect:/read/" + articleId;
    }

    @GetMapping("/comment/delete-view/{commentId}")
    public String deleteCommentView(@PathVariable("commentId") Long commentId, Model model) {
        Comment comment = commentService.getCommentById(commentId); // 댓글 조회
        if (comment != null && comment.getArticleNum() != null) {
            model.addAttribute("articleId", comment.getArticleNum().getId()); // 게시글 ID 추가
        }
        model.addAttribute("commentId", commentId);
        return "comments/delete-view";
    }
}
