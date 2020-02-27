package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.controller.dto.GenreDto;

public interface GenreService {

    GenreDto saveGenre(GenreDto genre);

    void deleteGenre(Long genreId);
    
    Optional<GenreDto> getGenreById(Long genreId);
    
    List<GenreDto> getGenresByParams(String filter);
    
    List<GenreDto> getAllGenres();
}
