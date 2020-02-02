package ru.otus.lib.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.domain.Author;
import ru.otus.lib.service.AuthorService;

@RequiredArgsConstructor
@Controller
public class AuthorController {

    private final AuthorService authorService;
    
    @PreAuthorize("hasAnyAuthority('USER, ADMIN')")
    @GetMapping("/authors")
    public String findAllAuthors(Model model) {
        List<Author> genreList = authorService.getAll();
        model.addAttribute("authors", genreList);
        return "author-list";
    }
}
