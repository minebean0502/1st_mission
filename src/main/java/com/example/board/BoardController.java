package com.example.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {

    @RequestMapping("chaewoon")
    public String home() {
        return "chaewoon.html";
    }
}
