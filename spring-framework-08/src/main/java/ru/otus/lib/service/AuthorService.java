package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.Author;

public interface AuthorService {

    Author createAuthor();

    Author updateAuthor();
    
    void deleteAuthor();
    
    Optional<Author> getById();

    Optional<Author> getById(String authorId);

    List<Author> getAll();
    
    boolean checkAuthorExists(String authorId);

}
