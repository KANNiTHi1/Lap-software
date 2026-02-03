package com.app.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    public PageController() {
        System.out.println("🔥 PageController LOADED");
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
