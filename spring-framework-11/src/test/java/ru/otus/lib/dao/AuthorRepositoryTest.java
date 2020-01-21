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
import ru.otus.lib.domain.Author;
import ru.otus.lib.repository.AuthorRepository;

@DisplayName("AuthorRepository tests")
@DataMongoTest
public class AuthorRepositoryTest {

    private Author authorLeoTolstoy = Author.builder().lastname("Tolstoy").firstname("Leo").middlename("Nikolaevich").build();
    private Author authorFedorDostoevsky = Author.builder().lastname("Dostoevsky").firstname("Fyodor").middlename("Mikhailovich").build();
    
    @Autowired
    private AuthorRepository authorDao;
    
    @BeforeEach
    public void before() {
        authorDao.deleteAll();
        authorDao.saveAll(Flux.just(authorLeoTolstoy, authorFedorDostoevsky)).blockLast();
    }
    
    @DisplayName("Search of all authors")
    @Test
    void testGetAll() {
        Flux<Author> authors = authorDao.findAll();
        StepVerifier.create(authors)
            .recordWith(ArrayList::new)
            .expectNextCount(2)
            .consumeRecordedWith(results -> {
                assertThat(results).hasSize(2);
                assertThat(results).contains(authorLeoTolstoy, authorFedorDostoevsky);
        }).expectComplete().verify();
    }

}
