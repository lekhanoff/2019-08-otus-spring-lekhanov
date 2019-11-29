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
import ru.otus.lib.repository.AuthorRepository;

@DisplayName("AuthorRepository tests")
@DataMongoTest
public class AuthorRepositoryTest {

    private Author authorLeoTolstoy = Author.builder().id("1000").lastname("Толстой").firstname("Лев").middlename("Николаевич").build();
    private Author authorFedorDostoevsky = Author.builder().id("1001").lastname("Достоевский").firstname("Федор").middlename("Михайлович").build();
    
    @Autowired
    private AuthorRepository authorDao;
    
    @BeforeEach
    public void before() {
        authorDao.deleteAll();
        authorDao.save(authorLeoTolstoy);
        authorDao.save(authorFedorDostoevsky);
    }
    
    @DisplayName("Author creation")
    @Test
    void testCreateAuthor() {
        Author authorAntonChekhov = Author.builder().lastname("Чехов").firstname("Антон").middlename("Павлович").build();
        
        Author author = authorDao.save(authorAntonChekhov);
        authorAntonChekhov.setId(author.getId());
        
        assertThat(author).isEqualTo(authorAntonChekhov);
    }

    @DisplayName("Author modification")
    @Test
    void testUpdateAuthor() {
        String authorId = "1000";
        Author authorForModify = Author.builder().id(authorId).lastname("ТОЛСТОЙ").firstname("ЛЕВ").middlename("НИКОЛАЕВИЧ").build();
        Author author = authorDao.save(authorForModify);
        assertThat(author).isEqualTo(authorForModify);
    }

    @DisplayName("Author deletion")
    @Test
    void testDeleteAuthor() {
        String authorId = "1001";
        authorDao.deleteById(authorId);
        assertThat(authorDao.findById(authorId)).isEmpty();
    }

    @DisplayName("Author search by id")
    @Test
    void testGetById() {
        Optional<Author> author = authorDao.findById("1000");
        assertThat(author.get()).isEqualTo(authorLeoTolstoy);
    }

    @DisplayName("Search of all authors")
    @Test
    void testGetAll() {
        List<Author> authorList = authorDao.findAll();
        assertThat(authorList).hasSize(2).containsExactly(authorLeoTolstoy, authorFedorDostoevsky);
    }

}
