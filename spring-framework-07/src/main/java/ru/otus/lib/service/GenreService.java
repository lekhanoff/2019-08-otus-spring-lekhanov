package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.Genre;

public interface GenreService {

    Genre createGenre();

    Genre updateGenre();
    
    void deleteGenre();
    
    Optional<Genre> getById();

    Optional<Genre> getById(Long genreId);

    Optional<Genre> getByName();

    List<Genre> getAll();

    boolean checkGenreExists(Long genreId);
}
