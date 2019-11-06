package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.Genre;

public interface GenreService {

    public Genre createGenre();

    public Genre updateGenre();
    
    public void deleteGenre();
    
    public Optional<Genre> getById();

    public Optional<Genre> getById(Long genreId);

    public Optional<Genre> getByName();

    public List<Genre> getAll();

    public boolean checkGenreExists(Long genreId);
}
