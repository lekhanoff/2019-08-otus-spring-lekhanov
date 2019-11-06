package ru.otus.lib.repository;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.Book;

public interface BookRepository {

    public Book createBook(Book book);

    public Book updateBook(Book book);
    
    public void deleteBook(Long bookId);
    
    public Optional<Book> getById(Long bookId);

    public List<Book> getAll();

    public List<Book> getBooksByAuthorId(Long authorId);

    public List<Book> getBooksByGenreId(Long genreId);

}
