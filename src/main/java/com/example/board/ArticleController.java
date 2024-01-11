package com.example.board;

import com.example.board.dto.ArticleDto;
import com.example.board.entity.Article;
import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final BoardService boardService;
    private final CommentService commentService;
    public ArticleController(ArticleService articleService, BoardService boardService, CommentService commentService) {
        this.articleService = articleService;
        this.boardService = boardService;
        this.commentService = commentService;
    }

    @GetMapping("/createArticle-view")
    public String createArticleView(Model model) {
        // 아래는 게시판 확인용
        List<Board> boards = boardService.readBoardAll();
        System.out.println(boards);
        model.addAttribute("boards", boardService.readBoardAll());
        return "article/create";
    }

    @PostMapping("article/create")
    public String createArticle(
            @RequestParam("title")
            String title,
            @RequestParam("content")
            String content,
            @RequestParam("password")
            String password,
            @RequestParam("classification-id")
            Long classificationId
    ) {
        articleService.create(title, content, password, classificationId);
        return String.format("redirect:/home/%s", classificationId);
    }

    @GetMapping("/home/1")
    public String board1Entire(Model model) {
        List<Article> articles = articleService.getArticlesOrderByIdDesc();
        model.addAttribute("articleList", articles);
        return "boards/board1Entire";
    }

    @GetMapping("/read/{id}")
    public String readOne(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        Article article = articleService.getArticleById(id);
        List<Comment> comments = commentService.getCommentsByArticleId(id);

        model.addAttribute("article", articleService.readArticle(id));
        model.addAttribute("comments", comments);
        return "article/read";
    }

    @GetMapping("/update-view/{id}")
    public String updateView(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        model.addAttribute("article", articleService.readArticle(id));
        return "article/update";
    }

    @PostMapping("/update/{id}")
    public String update(
            @PathVariable("id")
            Long id,
            @RequestParam("title")
            String title,
            @RequestParam("content")
            String content,
            @RequestParam("password")
            String password,
            RedirectAttributes redirectAttributes
    ) {
        try {
            articleService.update(id, title, content, password);
            return String.format("redirect:/read/%s", id);
        } catch (IllegalArgumentException e) {
            // 예외 발생시 처리
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return String.format("redirect:/update-view/%s", id);
        }
    }

    @GetMapping("/delete-view/{id}")
    public String deleteView(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        model.addAttribute("article", articleService.readArticle(id));
        return "article/delete";
    }

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable("id")
            Long id,
            @RequestParam("password")
            String password,
            RedirectAttributes redirectAttributes
    ) {
        try {
            // articleService에서 id값과 password값을 바탕으로 한번 삭제해보고
            articleService.delete(id, password);
            // 되면 전체 게시글 조회로 돌아가기
            return "redirect:/home/1";
        } catch (IllegalArgumentException e) { // 안된다면(id, password 오류가 생긴다면)
            // 오류 메세지를 띄우고 다시 delete-view로 리디렉트
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return String.format("redirect:/delete-view/%s", id);
        }
    }
}
