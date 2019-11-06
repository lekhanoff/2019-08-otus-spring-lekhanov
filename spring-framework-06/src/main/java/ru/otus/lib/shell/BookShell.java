package ru.otus.lib.shell;

import java.io.IOException;
import java.util.List;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.otus.lib.domain.Book;
import ru.otus.lib.service.BookService;

@ShellComponent
public class BookShell {

    private final BookService bookService;
    
    private final ObjectMapper objectMapper;

    public BookShell(BookService bookService, ObjectMapper objectMapper) {
        this.bookService = bookService;
        this.objectMapper = objectMapper;
    }

    @ShellMethod(value = "Book creation", key = {"book-create", "bc"})
    public String createBook() throws IOException {
        Book book = bookService.createBook();
        return objectMapper.writeValueAsString(book);
    }

    @ShellMethod(value = "Book modification", key = {"book-update", "bu"})
    public String updateBook() throws IOException {
        Book book = bookService.updateBook();
        return objectMapper.writeValueAsString(book);
    }

    @ShellMethod(value = "Find book by id", key = {"book-find-id", "bfi"})
    public String bookFindById() throws IOException {
        return objectMapper.writeValueAsString(bookService.getById().orElse(null));
    }
    
    @ShellMethod(value = "Find all books", key = {"book-find-all", "bfa"})
    public String bookFindAll() throws IOException {
        List<Book> bookList = bookService.getAll();
        return objectMapper.writeValueAsString(bookList);
    }

    @ShellMethod(value = "Find books by author identifier", key = {"book-find-author-id", "bfai"})
    public String bookFindByAuthorId() throws IOException {
        List<Book> bookList = bookService.getBooksByAuthorId();
        return objectMapper.writeValueAsString(bookList);
    }

    @ShellMethod(value = "Find books by author identifier", key = {"book-find-genre-id", "bfgi"})
    public String bookFindByGenreId() throws IOException {
        List<Book> bookList = bookService.getBooksByGenreId();
        return objectMapper.writeValueAsString(bookList);
    }
    
    @ShellMethod(value = "Book deletion", key = {"book-delete", "bd"})
    public void deleteBook() throws IOException {
        bookService.deleteBook();
    }

}
