package ru.otus.lib.dao;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.Book;

public interface BookDao {

    public Book createBook(Book book);

    public Book updateBook(Long bookId, Book book);
    
    public void deleteBook(Long bookId);
    
    public Optional<Book> getById(Long bookId);

    public List<Book> getAll();

    public List<Book> getBooksByAuthorId(Long authorId);

    public List<Book> getBooksByGenreId(Long genreId);

}
