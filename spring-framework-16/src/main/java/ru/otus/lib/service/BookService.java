package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.controller.dto.BookDto;

public interface BookService {

    BookDto saveBook(BookDto book);

    void deleteBook(Long bookId);
    
    Optional<BookDto> getBookById(Long bookId);
    
    List<BookDto> getAllBooks();

    List<BookDto> getBooksByParams(String filter);
}
