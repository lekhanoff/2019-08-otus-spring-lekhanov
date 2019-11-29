package ru.otus.lib.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.GenreRepository;

@DisplayName("GenreRepository tests")
@DataMongoTest
public class GenreRepositoryTest {
    
    @Autowired
    private GenreRepository genreDao;
    
    private Genre russianClassic = Genre.builder().id("1000").name("Русская классика").build();
    private Genre foreignClassic = Genre.builder().id("1001").name("Зарубежная классика").build();
    private Genre fantasy = Genre.builder().id("1002").name("Фэнтези").build();
    
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
        Genre scienceFiction = Genre.builder().name("Научная фантастика").build();
        Genre createdGenre = genreDao.save(scienceFiction);
        scienceFiction.setId(createdGenre.getId());
        assertThat(createdGenre.getId()).isNotNull();
        assertThat(createdGenre).isEqualTo(scienceFiction);
        
    }

    @DisplayName("Genre modification")
    @Test
    void testUpdateGenre() {
        Genre genreForUpdate = Genre.builder().id("1002").name("Fantasy").build();
        Genre result = genreDao.save(genreForUpdate);
        assertThat(result).isEqualTo(genreForUpdate);
    }

    @DisplayName("Genre deletion")
    @Test
    void testDeleteGenre() {
        String genreId = "1002";
        genreDao.deleteById(genreId);
        assertThat(genreDao.findById(genreId)).isEmpty();
    }

    @DisplayName("Search genre by id")
    @Test
    void testGetById() {
        String genreId = "1000";
        Optional<Genre> genre = genreDao.findById(genreId);
        assertThat(genre.get()).isEqualTo(russianClassic);
    }

    @DisplayName("Search genre by name")
    @Test
    void testGetByName() {
        Optional<Genre> genre = genreDao.findByName("Русская классика");
        assertThat(genre.get()).isEqualTo(russianClassic);
    }

    @DisplayName("Search of all genres")
    @Test
    void testGetAll() {
        List<Genre> genreList = genreDao.findAll();
        assertThat(genreList).hasSize(3).containsExactly(russianClassic, foreignClassic, fantasy);
    }

}
