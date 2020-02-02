package ru.otus.lib.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.service.GenreService;

@RequiredArgsConstructor
@Controller
public class GenreController {

    private final GenreService genreService;
    
    @GetMapping("/genres")
    public String findAllGenres(Model model) {
        List<Genre> genreList = genreService.getAll();
        model.addAttribute("genres", genreList);
        return "genre-list";
    }
}
