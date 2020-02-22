package ru.otus.lib.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.controller.dto.AuthorDto;
import ru.otus.lib.controller.dto.BookDto;
import ru.otus.lib.controller.dto.GenreDto;
import ru.otus.lib.domain.Book;
import ru.otus.lib.repository.BookRepository;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookDao;

    @HystrixCommand(fallbackMethod = "getDefaultBook", commandKey = "books")
    @Override
    public BookDto saveBook(BookDto book) {
        Book savedBook = bookDao.save(Book.fromDto(book));
        return BookDto.toDto(savedBook);
    }

    @HystrixCommand(fallbackMethod = "defaultDeleteBook", commandKey = "books")
    @Override
    public void deleteBook(Long bookId) {
        bookDao.deleteById(bookId);
    }

    @HystrixCommand(fallbackMethod = "getDefaultBooks", commandKey = "books")
    @Override
    public List<BookDto> getBooksByParams(String filter) {
        return bookDao.findByParams(filter).stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @HystrixCommand(fallbackMethod = "getDefaultBooks", commandKey = "books")
    @Override
    public List<BookDto> getAllBooks() {
        return bookDao.findAll().stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @HystrixCommand(fallbackMethod = "getDefaultBook", commandKey = "books")
    @Override
    public Optional<BookDto> getBookById(Long bookId) {
        return Optional.ofNullable(BookDto.toDto(bookDao.findById(bookId).orElse(null)));
    }

    public List<BookDto> getDefaultBooks() {
        return Arrays.asList(BookDto.builder().id(Long.valueOf(-1))
                .author(AuthorDto.builder().id(Long.valueOf(-1)).lastname("Doe").firstname("John").middlename("Jr.").build())
                .title("No name book")
                .genre(GenreDto.builder().id(Long.valueOf(-1)).name("Mystery").build())
                .build());
    }
    
    public BookDto getDefaultBook() {
        return getDefaultBooks().get(0);
    }

    public void defaultDeleteBook() {
    }


}
