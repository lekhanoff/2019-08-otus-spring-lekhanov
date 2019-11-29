package ru.otus.lib.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.jline.reader.LineReader;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ru.otus.lib.domain.Author;
import ru.otus.lib.domain.Book;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.exception.AuthorNotFoundException;
import ru.otus.lib.exception.BookNotFoundException;
import ru.otus.lib.exception.GenreNotFoundException;
import ru.otus.lib.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookDao;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final MessageSource messageSource;
    
    private final LineReader reader;

    public BookServiceImpl(BookRepository bookDao, AuthorService authorService, GenreService genreService, MessageSource messageSource,
            @Lazy LineReader reader) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
        this.messageSource = messageSource;
        this.reader = reader;
    }

    @Override
    public Book createBook() {
        String authorId = reader.readLine(messageSource.getMessage("book.enter.author-id", null, Locale.getDefault()));
        Optional<Author> author = authorService.getById(authorId);
        
        String genreId = reader.readLine(messageSource.getMessage("book.enter.genre-id", null, Locale.getDefault()));
        Optional<Genre> genre = genreService.getById(genreId);
        
        String title = reader.readLine(messageSource.getMessage("book.enter.title", null, Locale.getDefault()));
        
        return bookDao.save(Book.builder()
                .author(author.orElseThrow(() -> new AuthorNotFoundException(messageSource.getMessage("author.not-found", new Object[] {authorId}, Locale.getDefault()))))
                .genre(genre.orElseThrow(() -> new GenreNotFoundException(messageSource.getMessage("genre.not-found", new Object[] {authorId}, Locale.getDefault()))))
                .title(title)
                .build());
    }

    @Override
    public Book updateBook() {
        String bookId = reader.readLine(messageSource.getMessage("book.update.get-id", null, Locale.getDefault()));
        checkBookExists(bookId);
        
        String authorId = reader.readLine(messageSource.getMessage("book.update.author-id", null, Locale.getDefault()));
        Optional<Author> author = authorService.getById(authorId);
        
        String genreId = reader.readLine(messageSource.getMessage("book.update.genre-id", null, Locale.getDefault()));
        Optional<Genre> genre = genreService.getById(genreId);
        
        String title = reader.readLine(messageSource.getMessage("book.update.title", null, Locale.getDefault()));
        
        return bookDao.save(
                Book.builder()
                        .id(bookId)
                        .author(author.orElseThrow(() -> new AuthorNotFoundException(messageSource.getMessage("author.not-found", new Object[] {authorId}, Locale.getDefault()))))
                        .genre(genre.orElseThrow(() -> new GenreNotFoundException(messageSource.getMessage("genre.not-found", new Object[] {authorId}, Locale.getDefault()))))
                        .title(title)
                        .build());
    }

    @Override
    public void deleteBook() {
        String bookId = reader.readLine(messageSource.getMessage("book.delete", null, Locale.getDefault()));
        bookDao.deleteById(bookId);
        System.out.println(messageSource.getMessage("operation.successful", null, Locale.getDefault()));
    }

    @Override
    public Optional<Book> getById() {
        String bookId = reader.readLine(messageSource.getMessage("book.get-by-id", null, Locale.getDefault()));
        return bookDao.findById(bookId);
    }

    @Override
    public Optional<Book> getById(String bookId) {
        return bookDao.findById(bookId);
    }
    
    @Override
    public List<Book> getAll() {
        return bookDao.findAll();
    }

    @Override
    public List<Book> getBooksByAuthorId() {
        String authorId = reader.readLine(messageSource.getMessage("book.get-by-author-id", null, Locale.getDefault()));
        return bookDao.findByAuthorId(authorId);
    }
    
    @Override
    public List<Book> getBooksByAuthorId(String authorId){
        return bookDao.findByAuthorId(authorId);
    }

    @Override
    public List<Book> getBooksByGenreId() {
        String genreId = reader.readLine(messageSource.getMessage("book.get-by-genre-id", null, Locale.getDefault()));
        return bookDao.findByGenreId(genreId);
    }
    
    @Override
    public List<Book> getBooksByGenreId(String genreId) {
        return bookDao.findByGenreId(genreId);
    }

    @Override
    public boolean checkBookExists(String bookId) {
        boolean exists = bookDao.existsById(bookId); 
        if(exists) {
            return true;
        } else {
            throw new BookNotFoundException(reader.readLine(messageSource.getMessage("book.not-found", new Object[] {bookId}, Locale.getDefault())));
        }
    }
}
