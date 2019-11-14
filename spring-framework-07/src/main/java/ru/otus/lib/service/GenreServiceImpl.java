package ru.otus.lib.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.jline.reader.LineReader;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ru.otus.lib.domain.Genre;
import ru.otus.lib.exception.GenreContainsLinksException;
import ru.otus.lib.exception.GenreNotFoundException;
import ru.otus.lib.repository.GenreRepository;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreRepository genreDao;
    
    private final BookService bookService;
    
    private final MessageSource messageSource;
    
    private final LineReader reader;

    public GenreServiceImpl(GenreRepository genreDao, @Lazy BookService bookService, MessageSource messageSource, @Lazy LineReader reader) {
        this.genreDao = genreDao;
        this.bookService = bookService;
        this.messageSource = messageSource;
        this.reader = reader;
    }

    @Override
    public Genre createGenre() {
        String genreName = reader.readLine(messageSource.getMessage("genre.create", null, Locale.getDefault()));
        return genreDao.save(Genre.builder().name(genreName).build());
    }

    @Override
    public Genre updateGenre() {
        Long genreId = Long.valueOf(reader.readLine(messageSource.getMessage("genre.update-get-id", null, Locale.getDefault())));
        
        checkGenreExists(genreId);
        
        String genreName = reader.readLine(messageSource.getMessage("genre.update-get-name", null, Locale.getDefault()));
        
        return genreDao.save(Genre.builder().genreId(genreId).name(genreName).build());
    }

    @Override
    public void deleteGenre() {
        Long genreId = Long.valueOf(reader.readLine(messageSource.getMessage("genre.delete", null, Locale.getDefault())));
        
        if(bookService.getBooksByGenreId(genreId).size() > 0) {
            throw new GenreContainsLinksException(messageSource.getMessage("genre.delete-contains-links", new Object[] {genreId}, Locale.getDefault()));
        }
        
        genreDao.deleteById(genreId);
        System.out.println(messageSource.getMessage("operation.successful", null, Locale.getDefault()));
    }

    @Override
    public Optional<Genre> getById() {
        String genreId = reader.readLine(messageSource.getMessage("genre.get-by-id", null, Locale.getDefault()));
        return genreDao.findById(Long.valueOf(genreId));
    }

    @Override
    public Optional<Genre> getById(Long genreId) {
        return genreDao.findById(Long.valueOf(genreId));
    }
    
    @Override
    public Optional<Genre> getByName() {
        String genreName = reader.readLine(messageSource.getMessage("genre.get-by-name", null, Locale.getDefault()));
        return genreDao.findByName(genreName);
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.findAll();
    }

    @Override
    public boolean checkGenreExists(Long genreId) {
        boolean exists = genreDao.existsById(genreId);
        if(exists) {
            return true;
        } else {
            throw new GenreNotFoundException(messageSource.getMessage("genre.not-found", new Object[] {genreId}, Locale.getDefault()));
        }
    }
}
