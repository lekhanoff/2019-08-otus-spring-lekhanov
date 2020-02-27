package ru.otus.lib.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    @GetMapping("/v1/genres/{id}")
    public ResponseEntity<GenreDto> findGenreById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(genreService.getGenreById(id).orElse(GenreDto.builder().build()));
    }
    
    @PostMapping(value = "/v1/genres", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenreDto> createGenre(@RequestBody GenreDto genre) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genreService.saveGenre(genre));
    }
    
    @PutMapping(value = "/v1/genres/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenreDto> modifyGenre(@PathVariable(name = "id") Long id, @RequestBody GenreDto genre) {
        genre.setId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(genreService.saveGenre(genre));
    }

    @DeleteMapping("/v1/genres/{id}")
    public ResponseEntity<GenreDto> deleteGenreById(@PathVariable(name = "id") Long id) {
        ResponseEntity<GenreDto> responseEntity = ResponseEntity.ok(genreService.getGenreById(id).orElse(GenreDto.builder().build()));
        genreService.deleteGenre(id);
        return responseEntity;
    }

}
