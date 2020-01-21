package ru.otus.lib.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.GenreRepository;

@DisplayName("GenreRepository tests")
@DataMongoTest
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreDao;

    private Genre russianClassic = Genre.builder().name("Russian classic").build();
    private Genre foreignClassic = Genre.builder().name("Foreign classic").build();
    private Genre fantasy = Genre.builder().name("Fantasy").build();

    @BeforeEach
    public void before() {
        genreDao.deleteAll().block();
        genreDao.saveAll(Flux.just(russianClassic, foreignClassic, fantasy)).blockLast();
    }

    @DisplayName("Search of all genres")
    @Test
    void testGetAll() {
        Flux<Genre> genres = genreDao.findAll();
        StepVerifier.create(genres)
            .recordWith(ArrayList::new)
            .expectNextCount(3)
            .consumeRecordedWith(results -> {
                assertThat(results).hasSize(3);
                assertThat(results).contains(russianClassic, foreignClassic, fantasy);
        }).expectComplete().verify();
    }

}
