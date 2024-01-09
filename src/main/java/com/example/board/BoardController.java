package com.example.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
//@RequiredArgsConstructor
public class BoardController {

    @RequestMapping("home")
    public String homepage() {
        return "home.html";
    }

    @RequestMapping("home/1")
    public String board1Entire() {
        System.out.println("home/1가 실행되었음");
        return "boards/board1Entire.html";
    }
    @RequestMapping("/home/2")
    public String board2Free3() {
        System.out.println("home/2가 실행되었음");
        return "boards/board2Free.html";
    }
    @RequestMapping("/home/3")
    public String board3Develop() {
        System.out.println("home/3가 실행되었음");
        return "boards/board3Develop.html";
    }
    @RequestMapping("/home/4")
    public String board4Daily() {
        System.out.println("home/4가 실행되었음");
        return "boards/board4Daily.html";
    }
    @RequestMapping("/home/5")
    public String board5Trouble() {
        System.out.println("home/5가 실행되었음");
        return "boards/board5Trouble.html";
    }

//    @RequestMapping("/home/6")
//    public String profiles() {
//        System.out.println("/home/3가 실행되었음");
//        return "profile.html";
//    }
}
