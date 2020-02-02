package ru.otus.lib.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.GenreRepository;

@DisplayName("GenreRepository tests")
@DataJpaTest
public class GenreRepositoryTest {
    
    @Autowired
    private GenreRepository genreDao;
    
    private Genre russianClassic = Genre.builder().name("Russian classic").build();
    private Genre foreignClassic = Genre.builder().name("Foreign classic").build();
    private Genre fantasy = Genre.builder().name("Fantasy").build();
    
    @BeforeEach
    public void before() {
        genreDao.deleteAll();
        
        genreDao.save(russianClassic);
        genreDao.save(foreignClassic);
        genreDao.save(fantasy);
    }

    
    @DisplayName("Genre creation")
    @Test
    void testCreateGenre() {
        Genre scienceFiction = Genre.builder().name("Science fiction").build();
        Genre createdGenre = genreDao.save(scienceFiction);
        scienceFiction.setId(createdGenre.getId());
        assertThat(createdGenre.getId()).isNotNull();
        assertThat(createdGenre).isEqualTo(scienceFiction);
        
    }

    @DisplayName("Genre modification")
    @Test
    void testUpdateGenre() {
        Genre genreForUpdate = Genre.builder().id(fantasy.getId()).name("FANTASY").build();
        Genre result = genreDao.save(genreForUpdate);
        assertThat(result).isEqualTo(genreForUpdate);
    }

    @DisplayName("Genre deletion")
    @Test
    void testDeleteGenre() {
        Long genreId = foreignClassic.getId();
        genreDao.deleteById(genreId);
        assertThat(genreDao.findById(genreId)).isEmpty();
    }

    @DisplayName("Search genre by id")
    @Test
    void testGetById() {
        Long genreId = russianClassic.getId();
        Optional<Genre> genre = genreDao.findById(genreId);
        assertThat(genre.get()).isEqualTo(russianClassic);
    }

    @DisplayName("Search genre by name")
    @Test
    void testGetByName() {
        Optional<Genre> genre = genreDao.findByName("Russian classic");
        assertThat(genre.get()).isEqualTo(russianClassic);
    }

    @DisplayName("Search of all genres")
    @Test
    void testGetAll() {
        List<Genre> genreList = genreDao.findAll();
        assertThat(genreList).hasSize(3).containsExactly(russianClassic, foreignClassic, fantasy);
    }

}
