package ru.otus.lib.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ru.otus.lib.domain.Author;
import ru.otus.lib.domain.Book;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.AuthorRepository;
import ru.otus.lib.repository.BookRepository;
import ru.otus.lib.repository.GenreRepository;

@DisplayName("BookRepository tests")
@DataJpaTest
public class BookRepositoryTest {

    private Author authorLeoTolstoy;
    private Genre russianClassic;
    private Book warAndPeace;
    private Book annaKarenina;
    
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

        authorLeoTolstoy = Author.builder().lastname("Tolstoy").firstname("Leo").middlename("Nikolaevich").build();
        authorRepo.save(authorLeoTolstoy);
        
        russianClassic = Genre.builder().name("Russian classic").build();
        genreRepo.save(russianClassic);
        
        warAndPeace = Book.builder()
                .author(authorLeoTolstoy)
                .genre(russianClassic)
                .title("War and peace")
                .build();
        annaKarenina = Book.builder()
                .author(authorLeoTolstoy)
                .genre(russianClassic)
                .title("Anna Karenina")
                .build();
        
        bookRepo.save(warAndPeace);
        bookRepo.save(annaKarenina);
    }
    
    
    @DisplayName("Book creation")
    @Test
    void testCreateBook() {
        Book voskresenie = Book.builder().author(authorLeoTolstoy).genre(russianClassic).title("Voskresenie").build();
        
        Book book = bookRepo.save(voskresenie);
        voskresenie.setId(book.getId());
        
        assertThat(book).isEqualTo(voskresenie);
    }

    @DisplayName("Book modification")
    @Test
    void testUpdateBook() {
        Book bookForModify = Book.builder().id(warAndPeace.getId()).author(authorLeoTolstoy).genre(russianClassic).title("WAR AND PEACE").build();
        Book book = bookRepo.save(bookForModify);
        assertThat(book).isEqualTo(bookForModify);
    }

    @DisplayName("Book modification")
    @Test
    void testDeleteBook() {
        Long bookId = annaKarenina.getId();
        bookRepo.deleteById(bookId);
        assertThat(bookRepo.findById(bookId)).isEmpty();
    }

    @DisplayName("Search book by id")
    @Test
    void testGetById() {
        Optional<Book> book = bookRepo.findById(warAndPeace.getId());
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
        List<Book> bookList = bookRepo.findByAuthorId(authorLeoTolstoy.getId());
        assertThat(bookList).hasSize(2).containsExactly(warAndPeace, annaKarenina);
    }

    @DisplayName("Search books by genre id")
    @Test
    void testGetBooksByGenreId() {
        List<Book> bookList = bookRepo.findByGenreId(russianClassic.getId());
        assertThat(bookList).hasSize(2).containsExactly(warAndPeace, annaKarenina);
    }

    @DisplayName("Search books by params")
    @Test
    void testGetByParams() {
        List<Book> bookList = bookRepo.findByParams("leo");
        assertThat(bookList).hasSize(2).containsExactly(warAndPeace, annaKarenina);
        
        bookList = bookRepo.findByParams("classic");
        assertThat(bookList).hasSize(2).containsExactly(warAndPeace, annaKarenina);
        
        bookList = bookRepo.findByParams("an");
        assertThat(bookList).hasSize(2).containsExactly(warAndPeace, annaKarenina);
        
        bookList = bookRepo.findByParams("Anna");
        assertThat(bookList).hasSize(1).containsExactly(annaKarenina);
    }

    @DisplayName("Get books count by genre id")
    @Test
    void testGetBooksCountByGenreId() {
        assertThat(bookRepo.countByGenreId(russianClassic.getId())).isEqualTo(2);
    }

    @DisplayName("Get books count by author id")
    @Test
    void testGetBooksCountByAuthorId() {
        assertThat(bookRepo.countByAuthorId(authorLeoTolstoy.getId())).isEqualTo(2);
    }
    
}
