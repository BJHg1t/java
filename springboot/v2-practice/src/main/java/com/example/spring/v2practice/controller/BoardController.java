package com.example.spring.v2practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/")
    public String boardList() {
        return "board-list";
    }

    @GetMapping("/write")
    public String boardWrite() {
        return "board-write";
    }

}
