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
import ru.otus.lib.repository.AuthorRepository;

@DisplayName("AuthorRepository tests")
@DataJpaTest
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class
})
@DatabaseSetup("author-dataset.xml")
public class AuthorRepositoryTest {

    private Author authorLeoTolstoy = Author.builder().authorId(1000L).lastname("Толстой").firstname("Лев").middlename("Николаевич").build();
    private Author authorFedorDostoevsky = Author.builder().authorId(1001L).lastname("Достоевский").firstname("Федор").middlename("Михайлович").build();
    
    @Autowired
    private AuthorRepository authorDao;
    
    @DisplayName("Author creation")
    @Test
    void testCreateAuthor() {
        Author authorAntonChekhov = Author.builder().lastname("Чехов").firstname("Антон").middlename("Павлович").build();
        
        Author author = authorDao.save(authorAntonChekhov);
        authorAntonChekhov.setAuthorId(author.getAuthorId());
        
        assertThat(author).isEqualTo(authorAntonChekhov);
    }

    @DisplayName("Author modification")
    @Test
    void testUpdateAuthor() {
        Long authorId = Long.valueOf(1000);
        Author authorForModify = Author.builder().authorId(authorId).lastname("ТОЛСТОЙ").firstname("ЛЕВ").middlename("НИКОЛАЕВИЧ").build();
        Author author = authorDao.save(authorForModify);
        assertThat(author).isEqualTo(authorForModify);
    }

    @DisplayName("Author deletion")
    @Test
    void testDeleteAuthor() {
        Long authorId = Long.valueOf(1001);
        authorDao.deleteById(authorId);
        assertThat(authorDao.findById(authorId)).isEmpty();
    }

    @DisplayName("Author search by id")
    @Test
    void testGetById() {
        Optional<Author> author = authorDao.findById(Long.valueOf(1000));
        assertThat(author.get()).isEqualTo(authorLeoTolstoy);
    }

    @DisplayName("Search of all authors")
    @Test
    void testGetAll() {
        List<Author> authorList = authorDao.findAll();
        assertThat(authorList).hasSize(2).containsExactly(authorLeoTolstoy, authorFedorDostoevsky);
    }

}
