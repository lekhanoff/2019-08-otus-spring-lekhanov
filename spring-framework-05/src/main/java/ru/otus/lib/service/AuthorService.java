package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.Author;

public interface AuthorService {

    public Author createAuthor();

    public Author updateAuthor();
    
    public void deleteAuthor();
    
    public Optional<Author> getById();

    public List<Author> getAll();

    public boolean checkAuthorExists(Long authorId);

}
