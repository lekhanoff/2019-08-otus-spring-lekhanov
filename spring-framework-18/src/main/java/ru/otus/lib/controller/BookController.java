package ru.otus.lib.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.controller.dto.BookDto;
import ru.otus.lib.service.BookService;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;
    
    @GetMapping("/v1/books")
    public ResponseEntity<List<BookDto>> findBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/v1/books/{id}")
    public ResponseEntity<BookDto> findBookById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(bookService.getBookById(id).orElse(BookDto.builder().build()));
    }
    
    @PostMapping(value = "/v1/books", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(book));
    }
    
    @PutMapping(value = "/v1/books/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> modifyBook(@PathVariable(name = "id") Long id, @RequestBody BookDto book) {
        book.setId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(book));
    }

    @DeleteMapping("/v1/books/{id}")
    public ResponseEntity<BookDto> deleteBookById(@PathVariable(name = "id") Long id) {
        ResponseEntity<BookDto> responseEntity = ResponseEntity.ok(bookService.getBookById(id).orElse(BookDto.builder().build()));
        bookService.deleteBook(id);
        return responseEntity;
    }

}
