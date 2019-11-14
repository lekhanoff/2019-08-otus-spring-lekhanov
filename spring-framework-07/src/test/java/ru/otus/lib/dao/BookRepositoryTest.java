package ru.otus.lib.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import ru.otus.lib.domain.Author;
import ru.otus.lib.domain.Book;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.AuthorRepository;
import ru.otus.lib.repository.BookRepository;
import ru.otus.lib.repository.GenreRepository;

@DisplayName("BookRepository tests")
@DataJpaTest
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class
})
@DatabaseSetup({"author-dataset.xml", "book-dataset.xml", "genre-dataset.xml"})
public class BookRepositoryTest {

    private Author authorLeoTolstoy = Author.builder().authorId(1000L).lastname("Толстой").firstname("Лев").middlename("Николаевич").build();
    
    private Genre russianClassic = Genre.builder().genreId(1000L).name("Русская классика").build();
    
    private Book warAndPeace = Book.builder()
            .bookId(1000L)
            .author(authorLeoTolstoy)
            .genre(russianClassic)
            .title("Война и мир")
            .build();
    private Book annaKarenina = Book.builder()
            .bookId(1001L)
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

    @DisplayName("Book creation")
    @Test
    void testCreateBook() {
        Optional<Author> author = authorRepo.findById(1000L);
        Optional<Genre> genre = genreRepo.findById(1000L);
        
        Book voskresenie = Book.builder().author(author.get()).genre(genre.get()).title("Воскресенье").build();
        
        Book book = bookRepo.save(voskresenie);
        voskresenie.setBookId(book.getBookId());
        
        assertThat(book).isEqualTo(voskresenie);
    }

    @DisplayName("Book modification")
    @Test
    void testUpdateBook() {
        Long bookId = Long.valueOf(1000);
        Book bookForModify = Book.builder().bookId(bookId).author(authorLeoTolstoy).genre(russianClassic).title("ВОЙНА И МИР").build();
        
        Book book = bookRepo.save(bookForModify);
        assertThat(book).isEqualTo(bookForModify);
    }

    @DisplayName("Book modification")
    @Test
    void testDeleteBook() {
        Long bookId = Long.valueOf(1001);
        bookRepo.deleteById(bookId);
        assertThat(bookRepo.findById(bookId)).isEmpty();
    }

    @DisplayName("Search book by id")
    @Test
    void testGetById() {
        Optional<Book> book = bookRepo.findById(Long.valueOf(1000));
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
        List<Book> bookList = bookRepo.findByAuthorAuthorId(Long.valueOf(1000));
        assertThat(bookList).hasSize(2).containsExactly(warAndPeace, annaKarenina);
    }

    @DisplayName("Search books by genre id")
    @Test
    void testGetBooksByGenreId() {
        List<Book> bookList = bookRepo.findByGenreGenreId(Long.valueOf(1000));
        assertThat(bookList).hasSize(2).containsExactly(warAndPeace, annaKarenina);
    }

}
