package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.Book;

public interface BookService {

    Book createBook();

    Book updateBook();
    
    void deleteBook();
    
    Optional<Book> getById();

    Optional<Book> getById(String bookId);

    List<Book> getAll();

    List<Book> getBooksByAuthorId();

    List<Book> getBooksByAuthorId(String authorId);

    List<Book> getBooksByGenreId();
    
    List<Book> getBooksByGenreId(String genreId);

    boolean checkBookExists(String bookId);

}
