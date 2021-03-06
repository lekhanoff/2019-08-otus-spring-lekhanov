package ru.otus.lib.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.jline.reader.LineReader;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ru.otus.lib.domain.Author;
import ru.otus.lib.exception.AuthorContainsLinksException;
import ru.otus.lib.exception.AuthorNotFoundException;
import ru.otus.lib.repository.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorDao;
    
    private final BookService bookService;
    
    private final MessageSource messageSource;
    
    private final LineReader reader;

    public AuthorServiceImpl(AuthorRepository authorDao, @Lazy BookService bookService, MessageSource messageSource, @Lazy LineReader reader) {
        this.authorDao = authorDao;
        this.bookService = bookService;
        this.messageSource = messageSource;
        this.reader = reader;
    }

    @Override
    public Author createAuthor() {
        String lastname = reader.readLine(messageSource.getMessage("author.enter.lastname", null, Locale.getDefault()));
        String firstname = reader.readLine(messageSource.getMessage("author.enter.firstname", null, Locale.getDefault()));
        String middlename = reader.readLine(messageSource.getMessage("author.enter.middlename", null, Locale.getDefault()));
        
        return authorDao.createAuthor(Author.builder().lastname(lastname).firstname(firstname).middlename(middlename).build());
    }

    @Override
    public Author updateAuthor() {
        Long authorId = Long.valueOf(reader.readLine(messageSource.getMessage("author.update.get-id", null, Locale.getDefault())));
        
        checkAuthorExists(authorId);
        
        String lastname = reader.readLine(messageSource.getMessage("author.update.lastname", null, Locale.getDefault()));
        String firstname = reader.readLine(messageSource.getMessage("author.update.firstname", null, Locale.getDefault()));
        String middlename = reader.readLine(messageSource.getMessage("author.update.middlename", null, Locale.getDefault()));
        
        return authorDao.updateAuthor(Author.builder().authorId(authorId).lastname(lastname).firstname(firstname).middlename(middlename).build());
    }

    @Override
    public void deleteAuthor() {
        Long authorId = Long.valueOf(reader.readLine(messageSource.getMessage("author.delete", null, Locale.getDefault())));
        
        if(bookService.getBooksByAuthorId(authorId).size() > 0) {
            throw new AuthorContainsLinksException(messageSource.getMessage("author.delete-contains-links", new Object[] {authorId}, Locale.getDefault()));
        }
        
        authorDao.deleteAuthor(authorId);
        System.out.println(messageSource.getMessage("operation.successful", null, Locale.getDefault()));
    }

    @Override
    public Optional<Author> getById() {
        String authorId = reader.readLine(messageSource.getMessage("author.get-by-id", null, Locale.getDefault()));
        return authorDao.getById(Long.valueOf(authorId));
    }

    @Override
    public Optional<Author> getById(Long authorId) {
        return authorDao.getById(authorId);
    }
    
    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Override
    public boolean checkAuthorExists(Long authorId) {
        try {
            authorDao.getById(authorId);
            return true;
        } catch (DataAccessException e) {
            throw new AuthorNotFoundException(reader.readLine(messageSource.getMessage("author.not-found", new Object[] {authorId}, Locale.getDefault())), e);
        }
    }

}
