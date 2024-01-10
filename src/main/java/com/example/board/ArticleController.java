package com.example.board;

import com.example.board.dto.ArticleDto;
import com.example.board.entity.Board;
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
//    private final CommentService commentService;
    public ArticleController(ArticleService articleService, BoardService boardService) {
        this.articleService = articleService;
        this.boardService = boardService;
    }

    // # 기능 수정(view)입니다: 01-11
    // 1. 사용자에게 표기하고 싶은 메세지를 전달할 수 있는 HTML 반환
    // GetMapping -> send (보낼 때, 사용자가 데이터를 입력할곳에서)
    // 사용자가 게시글을 생성(입력)하는 곳임(사용자로부터 메세지 전달)
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
        // create-view에서 Controller의 create로 보내면
        // 게시글 목록들을 보기 위해 전체게시판 (/home/1)로 보내줌
        // ArticleDto article = service.createArticle(title, content, password);
        articleService.create(title, content, password, classificationId);
        return String.format("redirect:/home/%s", classificationId);
    }

    @GetMapping("/home/1")
    public String board1Entire(Model model) {
        model.addAttribute("articleList", articleService.readArticleAll());
        return "boards/board1Entire";
    }
    // # 기능 이전입니다 : 01-11
    // /read로 요청을 받으면
    // article/read.html에 articleList를 포함해 반환하는 메서드
    // Mapping에 {}를 넣으면 그 안에 들어가 있는 데이터를
    // 매개변수에 할당해 줄 수 있다
    // @GetMapping의 {}와, @PathVariable()을 일치시키면 됨
    @GetMapping("/read/{id}")
    public String readOne(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        model.addAttribute("article", articleService.readArticle(id));
        return "article/read";
    }

    // # 기능 수정(view)입니다: 01-11
    // /update-view/{id}로 요청 받으면
    // board/update.html에 article 정보를 포함해 반환하는 메서드
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
//        ArticleDto dto = articleService.updateArticle(id, title, content, password);
//        if (dto == null) {
//            // service의 dto의 비밀번호 matching에서 오류가 생긴다면
//            // 오류 메세지 출력(update.html에 표시) 및 다시 수정 페이지로 리디렉트
//            redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
//            return String.format("redirect:/update-view/%s", id);
//        }
//        return String.format("redirect:/read/%s", dto.getId());
    }

    // # 기능 수정(view)입니다 : 01-11
    @GetMapping("/delete-view/{id}")
    public String deleteView(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        model.addAttribute("article", articleService.readArticle(id));
        return "article/delete";
    }

    // #기능 수정입니다: 01-11
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
//        if (articleService.deleteArticle(id, password)) {
//            return "redirect:/home/1";
//        }
//        else {
//            // 비밀번호 불일치시, 에러메세지 출력 및 delete-view로 리디렉트
//            redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
//            return String.format("redirect:/delete-view/%s", id);
//        }
    }

}
