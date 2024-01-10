package com.example.board;

import com.example.board.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
//@RequiredArgsConstructor
public class BoardController {
    private final ArticleService articleService;
    private final BoardService boardService;

    public BoardController(ArticleService articleService, BoardService boardService) {
        this.articleService = articleService;
        this.boardService = boardService;
    }

    @RequestMapping("home")
    public String homepage() {
        return "home.html";
    }

    @RequestMapping("home/1")
    public String board1Entire() {
        System.out.println("home/1가 실행되었음");
        return "boards/board1Entire";
    }
    @RequestMapping("/home/2")
    public String board2Free(Model model) {
        System.out.println("home/2가 실행되었음");
        List<Article> articles = articleService.getArticleByClassId(2L);
        model.addAttribute("articleList", articles);
        return "boards/board2Free";
    }
    @RequestMapping("/home/3")
    public String board3Develop(Model model) {
        System.out.println("home/3가 실행되었음");
        List<Article> articles = articleService.getArticleByClassId(3L);
        model.addAttribute("articleList", articles);
        return "boards/board3Develop";
    }
    @RequestMapping("/home/4")
    public String board4Daily(Model model) {
        System.out.println("home/4가 실행되었음");
        List<Article> articles = articleService.getArticleByClassId(4L);
        model.addAttribute("articleList", articles);
        return "boards/board2Free";
    }
    @RequestMapping("/home/5")
    public String board5Trouble(Model model) {
        System.out.println("home/5가 실행되었음");
        List<Article> articles = articleService.getArticleByClassId(5L);
        model.addAttribute("articleList", articles);
        return "boards/board2Free";
    }

//    @RequestMapping("/home/6")
//    public String profiles() {
//        System.out.println("/home/3가 실행되었음");
//        return "profile.html";
//    }
}
