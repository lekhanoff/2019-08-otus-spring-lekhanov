package ru.otus.lib.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ru.otus.lib.domain.Author;
import ru.otus.lib.repository.AuthorRepository;

@DisplayName("AuthorRepository tests")
@DataJpaTest
public class AuthorRepositoryTest {

    private Author authorLeoTolstoy = Author.builder().lastname("Tolstoy").firstname("Leo").middlename("Nikolaevich").build();
    private Author authorFedorDostoevsky = Author.builder().lastname("Dostoevsky").firstname("Fyodor").middlename("Mikhailovich").build();
    
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
        Author authorAntonChekhov = Author.builder().lastname("Chekhov").firstname("Anton").middlename("Pavlovich").build();
        
        Author author = authorDao.save(authorAntonChekhov);
        authorAntonChekhov.setId(author.getId());
        
        assertThat(author).isEqualTo(authorAntonChekhov);
    }

    @DisplayName("Author modification")
    @Test
    void testUpdateAuthor() {
        Long authorId = authorLeoTolstoy.getId();
        Author authorForModify = Author.builder().id(authorId).lastname("TOLSTOY").firstname("LEO").middlename("NIKOLAEVICH").build();
        Author author = authorDao.save(authorForModify);
        assertThat(author).isEqualTo(authorForModify);
    }

    @DisplayName("Author deletion")
    @Test
    void testDeleteAuthor() {
        Long authorId = authorFedorDostoevsky.getId();
        authorDao.deleteById(authorId);
        assertThat(authorDao.findById(authorId)).isEmpty();
    }

    @DisplayName("Author search by id")
    @Test
    void testGetById() {
        Optional<Author> author = authorDao.findById(authorLeoTolstoy.getId());
        assertThat(author.get()).isEqualTo(authorLeoTolstoy);
    }

    @DisplayName("Search of all authors")
    @Test
    void testGetAll() {
        List<Author> authorList = authorDao.findAll();
        assertThat(authorList).hasSize(2).containsExactly(authorLeoTolstoy, authorFedorDostoevsky);
    }

}
