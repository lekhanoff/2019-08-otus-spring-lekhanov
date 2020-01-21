package ru.otus.lib.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.lib.domain.Author;
import ru.otus.lib.domain.Book;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.BookRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @MockBean
    private BookRepository repository;

    @Autowired
    private BookController bookController;

    private WebTestClient client = null;

    private Author authorLeoTolstoy = Author.builder()
            .id("1000")
            .lastname("Толстой")
            .firstname("Лев")
            .middlename("Николаевич")
            .build();

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

    @BeforeEach
    public void init() throws Exception {
        client = WebTestClient.bindToController(bookController).build();

        Flux<Book> result = Flux.create(sink -> {
            sink.next(warAndPeace);
            sink.next(annaKarenina);
            sink.complete();
        });
        when(repository.findAll()).thenReturn(result);
        when(repository.findById(warAndPeace.getId())).thenReturn(Mono.just(warAndPeace));
        when(repository.save(warAndPeace)).thenReturn(Mono.just(warAndPeace));
    }

    @Test
    public void testSaveBook() throws Exception {
        client.post()
                .uri("/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(warAndPeace))
                .exchange()
                .expectStatus()
                .isOk();

        Mockito.verify(repository, times(1)).save(warAndPeace);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        client.get()
                .uri("/v1/books")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Book.class)
                .hasSize(2)
                .contains(warAndPeace, annaKarenina);
    }

    @Test
    public void testGetBookById() throws Exception {
        client.get()
                .uri("/v1/books/" + warAndPeace.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody()
                .equals(warAndPeace);
    }

}
