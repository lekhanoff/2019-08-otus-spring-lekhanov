package ru.otus.lib.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.GenreRepository;

@RequiredArgsConstructor
@RestController
public class GenreController {

    private final GenreRepository genreRepository;
    
    @GetMapping("v1/genres")
    public Flux<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

}
