package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.Book;

public interface BookService {

    Book createBook(Book book);

    Book updateBook(Book book);
    
    void deleteBook(Long bookId);
    
    Optional<Book> getBookById(Long bookId);
    
    List<Book> getAllBooks();

    List<Book> getBooksByParams(String filter);
}
