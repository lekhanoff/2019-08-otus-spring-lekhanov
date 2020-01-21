package ru.otus.lib.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.otus.lib.domain.Author;
import ru.otus.lib.domain.Book;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.AuthorRepository;
import ru.otus.lib.repository.BookRepository;
import ru.otus.lib.repository.GenreRepository;

@DisplayName("BookRepository tests")
@DataMongoTest
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
        authorRepo.deleteAll().block();
        genreRepo.deleteAll().block();
        bookRepo.deleteAll().block();

        authorLeoTolstoy = Author.builder().lastname("Tolstoy").firstname("Leo").middlename("Nikolaevich").build();
        authorRepo.save(authorLeoTolstoy).block();

        russianClassic = Genre.builder().name("Russian classic").build();
        genreRepo.save(russianClassic).block();

        warAndPeace = Book.builder().author(authorLeoTolstoy).genre(russianClassic).title("War and peace").build();
        annaKarenina = Book.builder().author(authorLeoTolstoy).genre(russianClassic).title("Anna Karenina").build();

        bookRepo.saveAll(Flux.just(warAndPeace, annaKarenina)).blockLast();
    }

    @DisplayName("Book creation")
    @Test
    void testCreateBook() {
        Book voskresenie = Book.builder().author(authorLeoTolstoy).genre(russianClassic).title("Voskresenie").build();
        StepVerifier.create(bookRepo.save(voskresenie))
                .assertNext(book -> assertNotNull(book.getId()))
                .expectComplete()
                .verify();
    }

    @DisplayName("Book modification")
    @Test
    void testUpdateBook() {
        Book bookForModify = Book.builder()
                .id(warAndPeace.getId())
                .author(authorLeoTolstoy)
                .genre(russianClassic)
                .title("WAR AND PEACE")
                .build();
        StepVerifier.create(bookRepo.save(bookForModify))
                .assertNext(book -> assertThat(book).isEqualTo(bookForModify))
                .expectComplete()
                .verify();
    }

    @DisplayName("Book deletion")
    @Test
    void testDeleteBook() {
        bookRepo.deleteById(annaKarenina.getId()).block();
        StepVerifier.create(bookRepo.findById(annaKarenina.getId()))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @DisplayName("Search book by id")
    @Test
    void testGetById() {
        StepVerifier.create(bookRepo.findById(warAndPeace.getId()))
                .assertNext(book -> assertThat(warAndPeace).isEqualTo(warAndPeace))
                .expectComplete()
                .verify();
    }

    @DisplayName("Search of all books")
    @Test
    void testGetAll() {
        Flux<Book> books = bookRepo.findAll();
        StepVerifier.create(books)
            .recordWith(ArrayList::new)
            .expectNextCount(2)
            .consumeRecordedWith(results -> {
                assertThat(results).hasSize(2);
                assertThat(results).contains(warAndPeace, annaKarenina);
        }).expectComplete().verify();
    }
}
