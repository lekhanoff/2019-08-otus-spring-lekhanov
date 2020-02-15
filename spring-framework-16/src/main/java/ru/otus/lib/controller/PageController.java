package ru.otus.lib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PageController {

    @GetMapping("/welcome")
    public String getWelcomePage(Model model) {
        return "index";
    }
}
