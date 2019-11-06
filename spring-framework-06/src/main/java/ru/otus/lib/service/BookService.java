package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.Book;

public interface BookService {

    public Book createBook();

    public Book updateBook();
    
    public void deleteBook();
    
    public Optional<Book> getById();

    public Optional<Book> getById(Long bookId);

    public List<Book> getAll();

    public List<Book> getBooksByAuthorId();

    public List<Book> getBooksByAuthorId(Long authorId);

    public List<Book> getBooksByGenreId();
    
    public List<Book> getBooksByGenreId(Long genreId);

    public boolean checkBookExists(Long bookId);


}
