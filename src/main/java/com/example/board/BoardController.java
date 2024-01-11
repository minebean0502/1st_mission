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
}
