package ru.otus.lib.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.jline.reader.LineReader;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ru.otus.lib.dao.BookDao;
import ru.otus.lib.domain.Book;
import ru.otus.lib.exception.BookNotFoundException;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final MessageSource messageSource;
    
    private final LineReader reader;

    public BookServiceImpl(BookDao bookDao, AuthorService authorService, GenreService genreService, MessageSource messageSource,
            @Lazy LineReader reader) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
        this.messageSource = messageSource;
        this.reader = reader;
    }

    @Override
    public Book createBook() {
        Long authorId = Long.valueOf(reader.readLine(messageSource.getMessage("book.enter.author-id", null, Locale.getDefault())));
        authorService.checkAuthorExists(authorId);
        
        Long genreId = Long.valueOf(reader.readLine(messageSource.getMessage("book.enter.genre-id", null, Locale.getDefault())));
        genreService.checkGenreExists(genreId);
        
        String title = reader.readLine(messageSource.getMessage("book.enter.title", null, Locale.getDefault()));
        
        return bookDao.createBook(Book.builder().authorId(authorId).genreId(genreId).title(title).build());
    }

    @Override
    public Book updateBook() {
        Long bookId = Long.valueOf(reader.readLine(messageSource.getMessage("book.update.get-id", null, Locale.getDefault())));
        checkBookExists(bookId);
        
        Long authorId = Long.valueOf(reader.readLine(messageSource.getMessage("book.update.author-id", null, Locale.getDefault())));
        authorService.checkAuthorExists(authorId);
        
        Long genreId = Long.valueOf(reader.readLine(messageSource.getMessage("book.update.genre-id", null, Locale.getDefault())));
        genreService.checkGenreExists(genreId);
        
        String title = reader.readLine(messageSource.getMessage("book.update.title", null, Locale.getDefault()));
        
        return bookDao.updateBook(bookId, Book.builder().authorId(authorId).genreId(genreId).title(title).build());
    }

    @Override
    public void deleteBook() {
        Long bookId = Long.valueOf(reader.readLine(messageSource.getMessage("book.delete", null, Locale.getDefault())));
        bookDao.deleteBook(bookId);
        System.out.println(messageSource.getMessage("operation.successful", null, Locale.getDefault()));
    }

    @Override
    public Optional<Book> getById() {
        String bookId = reader.readLine(messageSource.getMessage("book.get-by-id", null, Locale.getDefault()));
        return bookDao.getById(Long.valueOf(bookId));
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public List<Book> getBooksByAuthorId() {
        Long authorId = Long.valueOf(reader.readLine(messageSource.getMessage("book.get-by-author-id", null, Locale.getDefault())));
        return bookDao.getBooksByAuthorId(authorId);
    }
    
    @Override
    public List<Book> getBooksByAuthorId(Long authorId){
        return bookDao.getBooksByAuthorId(authorId);
    }

    @Override
    public List<Book> getBooksByGenreId() {
        Long genreId = Long.valueOf(reader.readLine(messageSource.getMessage("book.get-by-genre-id", null, Locale.getDefault())));
        return bookDao.getBooksByGenreId(genreId);
    }
    
    @Override
    public List<Book> getBooksByGenreId(Long genreId) {
        return bookDao.getBooksByGenreId(genreId);
    }


    @Override
    public boolean checkBookExists(Long bookId) {
        try {
            bookDao.getById(bookId);
            return true;
        } catch (DataAccessException e) {
            throw new BookNotFoundException(reader.readLine(messageSource.getMessage("book.not-found", new Object[] {bookId}, Locale.getDefault())), e);
        }
    }
    

}
