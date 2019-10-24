package ru.otus.lib.dao;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.Genre;

public interface GenreDao {

    public Genre createGenre(Genre genre);

    public Genre updateGenre(Long genreId, Genre genre);
    
    public void deleteGenre(Long genreId);
    
    public Optional<Genre> getById(Long genreId);

    public Optional<Genre> getByName(String name);

    public List<Genre> getAll();


}
