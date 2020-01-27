package ru.otus.lib.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.domain.Book;
import ru.otus.lib.exception.AuthorNotFoundException;
import ru.otus.lib.service.AuthorService;
import ru.otus.lib.service.BookService;
import ru.otus.lib.service.GenreService;

@RequiredArgsConstructor
@Controller
public class BookController {

    private static final String BOOK_DELETE = "book-delete";
    private static final String BOOK_EDIT = "book-edit";
    private static final String BOOK_CREATE = "book-create";
    private static final String BOOK = "book";
    private static final String BOOKS = "books";
    private static final String GENRES = "genres";
    private static final String AUTHORS = "authors";
    private static final String BOOK_LIST = "book-list";
    
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    
    @GetMapping("/books")
    public String findAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute(BOOKS, books);
        return BOOK_LIST;
    }
    
    @GetMapping("/book-search")
    public String findBooksByParams(@RequestParam(name="search") String filter, Model model) {
        List<Book> books = bookService.getBooksByParams(filter);
        model.addAttribute(BOOKS, books);
        return BOOK_LIST;
    }

    @GetMapping("/book-create")
    public String createBook(Model model) {
        model.addAttribute(BOOK, Book.builder().build());
        model.addAttribute(AUTHORS, authorService.getAll());
        model.addAttribute(GENRES, genreService.getAll());
        return BOOK_CREATE;
    }

    @PostMapping(value = "/books", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createBookAction(Book book, Model model) {
        bookService.createBook(book);
        List<Book> books = bookService.getAllBooks();
        model.addAttribute(BOOKS, books);
        return BOOK_LIST;
    }
    
    @GetMapping("/book-edit")
    public String editBook(
            @RequestParam(name = "id") Long bookId,
            Model model) {
        model.addAttribute(BOOK, bookService.getBookById(bookId).orElseThrow(()-> new AuthorNotFoundException("Not found")));
        model.addAttribute(AUTHORS, authorService.getAll());
        model.addAttribute(GENRES, genreService.getAll());
        
        return BOOK_EDIT;
    }
    
    @PutMapping(value = "/books", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String editBookAction(
            Book book, Model model) {
        bookService.updateBook(book);
        List<Book> books = bookService.getAllBooks();
        model.addAttribute(BOOKS, books);
        return BOOK_LIST;
    }
    
    @GetMapping("/book-delete")
    public String deleteBook(            
            @RequestParam(name = "id") Long bookId,
            Model model) {
        model.addAttribute(BOOK, bookService.getBookById(bookId).orElseThrow(()-> new AuthorNotFoundException("Not found")));
        model.addAttribute(AUTHORS, authorService.getAll());
        model.addAttribute(GENRES, genreService.getAll());
        return BOOK_DELETE;
    }

    @DeleteMapping(value = "/books", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String deleteBookAction(
            Book book, Model model) {
        bookService.deleteBook(book.getId());
        List<Book> books = bookService.getAllBooks();
        model.addAttribute(BOOKS, books);
        return BOOK_LIST;
        
    }
    
}
