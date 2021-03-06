package ru.otus.lib.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import ru.otus.lib.controller.dto.GenreDto;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.GenreRepository;

@DisplayName("Testing GenreService")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GenreServiceTest {

    @Mock
    private GenreRepository genreRepo;

    @Mock
    private BookService bookService;

    private GenreServiceImpl genreService;

    private Genre russianClassic = Genre.builder().id(Long.valueOf(1000)).name("Russian classic").build();
    private Genre foreignClassic = Genre.builder().id(Long.valueOf(1001)).name("Foreign classic").build();

    @BeforeEach
    public void init() throws IOException {
        genreService = new GenreServiceImpl(genreRepo, bookService);
        when(genreRepo.findAll()).thenReturn(Arrays.<Genre>asList(russianClassic, foreignClassic));
        when(bookService.getCountByGenreId(russianClassic.getId())).thenReturn(2);
        when(bookService.getCountByGenreId(foreignClassic.getId())).thenReturn(0);
    }

    @DisplayName("Genre find all")
    @Test
    public void testGetAll() {
        List<GenreDto> genres = genreService.getAllGenres();
        assertThat(genres).hasSize(2).containsExactly(GenreDto.toDto(russianClassic), GenreDto.toDto(foreignClassic));
        assertThat(Mockito.mockingDetails(genreRepo).getInvocations().size()).isEqualTo(1);
    }
    
    @DisplayName("Gengre save")
    @Test
    public void testSaveGenre() {
        when(genreRepo.save(russianClassic)).thenReturn(russianClassic);
        GenreDto genre = genreService.saveGenre(GenreDto.toDto(russianClassic));
        assertThat(genre).isEqualTo(GenreDto.toDto(russianClassic));
        assertThat(Mockito.mockingDetails(genreRepo).getInvocations().size()).isEqualTo(1);
    }

    @DisplayName("Genre delete")
    @Test
    public void testDeleteGenre() {
        genreService.deleteGenre(foreignClassic.getId());
        assertThat(Mockito.mockingDetails(genreRepo).getInvocations().size()).isEqualTo(1);
    }

    @DisplayName("Genre find by params")
    @Test
    public void testGetGenresByParams() {
        when(genreRepo.findByParams("filter")).thenReturn(Arrays.asList(russianClassic, foreignClassic));
        List<GenreDto> genres = genreService.getGenresByParams("filter");
        assertThat(genres).hasSize(2).containsExactly(GenreDto.toDto(russianClassic), GenreDto.toDto(foreignClassic));
        assertThat(Mockito.mockingDetails(genreRepo).getInvocations().size()).isEqualTo(1);
    }

    @DisplayName("Genre find by id")
    @Test
    public void testGetGenreById() {
        when(genreRepo.findById(russianClassic.getId())).thenReturn(Optional.of(russianClassic));
        Optional<GenreDto> genre = genreService.getGenreById(russianClassic.getId());
        assertThat(genre).isEqualTo(Optional.of(GenreDto.toDto(russianClassic)));
        assertThat(Mockito.mockingDetails(genreRepo).getInvocations().size()).isEqualTo(1);
    }
    
    @DisplayName("Histrix methods test")
    @Test
    public void histrixMethodsTest() {
        GenreDto genre = GenreDto.builder().id(Long.valueOf(-1)).name("Mystery").build();
        assertThat(genreService.getDefaultGenres()).hasSize(1).containsExactly(genre);
        assertThat(genreService.getDefaultGenresWithFilter("")).hasSize(1).containsExactly(genre);
        assertThat(genreService.getDefaultGenre(Long.valueOf(1)).orElse(null)).isEqualTo(genre);
        assertThat(genreService.defaultSaveGenre(GenreDto.builder().build())).isEqualTo(genre);
        genreService.defaultDeleteGenre(Long.valueOf(1));
    }
}
