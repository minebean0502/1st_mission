package com.example.board;

import com.example.board.entity.Article;
import com.example.board.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "home";
    }

    @RequestMapping("home/1")
    public String board1Entire() {
        System.out.println("home/1가 실행되었음");
        return "boards/board1Entire";
    }
    @RequestMapping("/home/{boardId}")
    public String boardOne(
            @PathVariable("boardId")
            Long boardId,
            Model model) {

        List<Article> articles = articleService.getArticlesByClassificationIdOrderByDesc(boardId);
        Board board = boardService.getBoardById(boardId);

        model.addAttribute("articleList", articles);
        model.addAttribute("board", board);
        return "boards/boardOne";
    }

    /* #기능 수정(제거): 01-11-08:22
    // 위의 boardOne에 모두 구현됨

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
     */
}
