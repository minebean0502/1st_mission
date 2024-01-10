package com.example.board;

import com.example.board.dto.ArticleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class ArticleController {

    private ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    // 1. 사용자에게 표기하고 싶은 메세지를 전달할 수 있는 HTML 반환
    // GetMapping -> send (보낼 때, 사용자가 데이터를 입력할곳에서)
    // 사용자가 게시글을 생성(입력)하는 곳임(사용자로부터 메세지 전달)
    @GetMapping("/createArticle-view")
    public String createArticleView() {
        System.out.println("createArticle-view까진 작동합니다");
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
            Model model
    ) {
        log.info(title);
        log.info(content);
        log.info(password);
//        model.addAttribute("title", title);
//        model.addAttribute("content", content);
        // create-view에서 Controller의 create로 보내면
        // 게시글 목록들을 보기 위해 /1로 보내줌
        ArticleDto article = service.createArticle(title, content, password);
        System.out.println("article/create까지는 왔습니다");
        return "redirect:/home/1";
    }

    @GetMapping("/home/1")
    public String board1Entire(Model model) {
        System.out.println("/home/1은 작동합니다");
        model.addAttribute("articleList", service.readArticleAll());
        return "boards/board1Entire";
    }

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
        System.out.println("readOne 왔어요");
        System.out.println("게시글 상세조회 페이지에요");
        ArticleDto dto = service.readArticle(id);
        model.addAttribute("article", dto);
        return "article/read";
    }

    // /update-view/{id}로 요청 받으면
    // board/update.html에 article 정보를 포함해 반환하는 메서드
    @GetMapping("/update-view/{id}")
    public String updateView(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        ArticleDto dto = service.readArticle(id);
        model.addAttribute("article", dto);
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
        log.info(title);
        log.info(content);
        ArticleDto dto = service.updateArticle(id, title, content, password);
        if (dto == null) {
            redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
            return String.format("redirect:/update-view/%s", id);
        }
        return String.format("redirect:/read/%s", dto.getId());
    }

    @GetMapping("/delete-view/{id}")
    public String deleteView(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        System.out.println("delete view까진 왔음");
        ArticleDto dto = service.readArticle(id);
        model.addAttribute("article", dto);
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
        System.out.println("delete까지 왔어용");
        //service.deleteArticle(id, password);
        if (service.deleteArticle(id, password)) {
            return "redirect:/home/1";
        }
        else {
            redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
            return String.format("redirect:/delete-view/%s", id);
        }
    }

    /*
    @GetMapping("read/{id}")
    public String readOne(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        ArticleDto dto = service.read
    }
    */

    /*
    // 1. 사용자에게 표기하고 싶은 메세지를 전달할 수 있는 HTML 반환
    // GetMapping -> send (보낼 때, 사용자가 데이터를 입력할곳에서)
    @RequestMapping("/send")
    public String getFrom() {
        return "article/send";
    }

    // 2. 사용자가 전달한 데이터를 처리할 수 있는 메서드
    // postMapping -> receive (받을 때, 입력이 저장되는 곳으로)
    @RequestMapping("/receive")
    public String receiveData(
            // @RequestParam : 사용자가 보낸 요청의
            // 데이터를 받는 목적의 매개변수임을 표기
            @RequestParam("message")
            String message,
            Model model
    ) {
        System.out.println(message);
        model.addAttribute("message", message);
        return "article/receive";
    }
     */

}
