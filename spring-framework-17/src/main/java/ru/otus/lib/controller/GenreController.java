package ru.otus.lib.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.controller.dto.GenreDto;
import ru.otus.lib.service.GenreService;

@RequiredArgsConstructor
@RestController
public class GenreController {

    private final GenreService genreService;
    
    @GetMapping("v1/genres")
    public ResponseEntity<List<GenreDto>> findAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

}
