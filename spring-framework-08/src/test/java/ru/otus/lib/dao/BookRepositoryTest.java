package ru.otus.lib.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import ru.otus.lib.domain.Author;
import ru.otus.lib.domain.Book;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.AuthorRepository;
import ru.otus.lib.repository.BookRepository;
import ru.otus.lib.repository.GenreRepository;

@DisplayName("BookRepository tests")
@DataMongoTest
public class BookRepositoryTest {

    private Author authorLeoTolstoy = Author.builder().id("1000").lastname("Толстой").firstname("Лев").middlename("Николаевич").build();
    
    private Genre russianClassic = Genre.builder().id("1000").name("Русская классика").build();
    
    private Book warAndPeace = Book.builder()
            .id("1000")
            .author(authorLeoTolstoy)
            .genre(russianClassic)
            .title("Война и мир")
            .build();
    private Book annaKarenina = Book.builder()
            .id("1001")
            .author(authorLeoTolstoy)
            .genre(russianClassic)
            .title("Анна Каренина")
            .build();
    
    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private GenreRepository genreRepo;

    @BeforeEach
    public void init() {
        authorRepo.deleteAll();
        genreRepo.deleteAll();
        bookRepo.deleteAll();
        
        authorRepo.save(authorLeoTolstoy);
        genreRepo.save(russianClassic);
        bookRepo.save(warAndPeace);
        bookRepo.save(annaKarenina);
    }
    
    
    @DisplayName("Book creation")
    @Test
    void testCreateBook() {
        Optional<Author> author = authorRepo.findById("1000");
        Optional<Genre> genre = genreRepo.findById("1000");
        
        Book voskresenie = Book.builder().author(author.get()).genre(genre.get()).title("Воскресенье").build();
        
        Book book = bookRepo.save(voskresenie);
        voskresenie.setId(book.getId());
        
        assertThat(book).isEqualTo(voskresenie);
    }

    @DisplayName("Book modification")
    @Test
    void testUpdateBook() {
        String bookId = "1000";
        Book bookForModify = Book.builder().id(bookId).author(authorLeoTolstoy).genre(russianClassic).title("ВОЙНА И МИР").build();
        
        Book book = bookRepo.save(bookForModify);
        assertThat(book).isEqualTo(bookForModify);
    }

    @DisplayName("Book modification")
    @Test
    void testDeleteBook() {
        String bookId = "1001";
        bookRepo.deleteById(bookId);
        assertThat(bookRepo.findById(bookId)).isEmpty();
    }

    @DisplayName("Search book by id")
    @Test
    void testGetById() {
        Optional<Book> book = bookRepo.findById("1000");
        assertThat(book.get()).isEqualTo(warAndPeace);
    }

    @DisplayName("Search of all books")
    @Test
    void testGetAll() {
        List<Book> bookList = bookRepo.findAll();
        assertThat(bookList).hasSize(2).containsExactly(warAndPeace, annaKarenina);
    }

    @DisplayName("Search books by author id")
    @Test
    void testGetBooksByAuthorId() {
        List<Book> bookList = bookRepo.findByAuthorId("1000");
        assertThat(bookList).hasSize(2).containsExactly(warAndPeace, annaKarenina);
    }

    @DisplayName("Search books by genre id")
    @Test
    void testGetBooksByGenreId() {
        List<Book> bookList = bookRepo.findByGenreId("1000");
        assertThat(bookList).hasSize(2).containsExactly(warAndPeace, annaKarenina);
    }

}
