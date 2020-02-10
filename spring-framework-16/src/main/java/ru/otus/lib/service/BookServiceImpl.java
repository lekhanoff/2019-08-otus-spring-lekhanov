package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.controller.dto.BookDto;
import ru.otus.lib.domain.Book;
import ru.otus.lib.repository.BookRepository;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookDao;

    @Override
    public BookDto saveBook(BookDto book) {
        Book savedBook = bookDao.save(Book.fromDto(book));
        return BookDto.toDto(savedBook);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookDao.deleteById(bookId);
    }

    @Override
    public List<BookDto> getBooksByParams(String filter) {
        return bookDao.findByParams(filter).stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookDao.findAll().stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<BookDto> getBookById(Long bookId) {
        return Optional.ofNullable(BookDto.toDto(bookDao.findById(bookId).orElse(null)));
    }
}
