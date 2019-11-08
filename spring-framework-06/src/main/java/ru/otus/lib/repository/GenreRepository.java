package ru.otus.lib.repository;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.Genre;

public interface GenreRepository {

    public Genre createGenre(Genre genre);

    public Genre updateGenre(Genre genre);
    
    public void deleteGenre(Long genreId);
    
    public Optional<Genre> getById(Long genreId);

    public Optional<Genre> getByName(String name);

    public List<Genre> getAll();

}
