package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.Author;

public interface AuthorService {

    Optional<Author> getById(Long authorId);

    List<Author> getAll();
}
