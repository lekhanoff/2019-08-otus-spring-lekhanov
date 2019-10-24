package ru.otus.lib.dao;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.Author;

public interface AuthorDao {

    public Author createAuthor(Author author);

    public Author updateAuthor(Long authorId, Author author);
    
    public void deleteAuthor(Long authorId);
    
    public Optional<Author> getById(Long authorId);

    public List<Author> getAll();

}
