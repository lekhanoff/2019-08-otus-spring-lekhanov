package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.Genre;

public interface GenreService {

    Optional<Genre> getById(Long genreId);

    List<Genre> getAll();
}
