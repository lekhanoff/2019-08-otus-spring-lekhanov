package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.domain.Book;
import ru.otus.lib.repository.BookRepository;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookDao;

    @Override
    public Book createBook(Book book) {
        return bookDao.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        return bookDao.save(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookDao.deleteById(bookId);
    }

    @Override
    public List<Book> getBooksByParams(String filter) {
        return bookDao.findByParams(filter);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public Optional<Book> getBookById(Long bookId) {
        return bookDao.findById(bookId);
    }
}
