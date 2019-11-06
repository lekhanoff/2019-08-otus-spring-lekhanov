package ru.otus.lib.repository;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.Author;

public interface AuthorRepository {

    public Author createAuthor(Author author);

    public Author updateAuthor(Author author);
    
    public void deleteAuthor(Long authorId);
    
    public Optional<Author> getById(Long authorId);

    public List<Author> getAll();

}
