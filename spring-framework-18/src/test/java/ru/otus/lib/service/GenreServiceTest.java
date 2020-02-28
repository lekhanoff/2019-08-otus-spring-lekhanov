package ru.otus.lib.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

    private GenreServiceImpl genreService;

    private Genre russianClassic = Genre.builder().id(Long.valueOf(1000)).name("Russian classic").build();
    private Genre foreignClassic = Genre.builder().id(Long.valueOf(1001)).name("Foreign classic").build();

    @BeforeEach
    public void init() throws IOException {
        genreService = new GenreServiceImpl(genreRepo);
        when(genreRepo.findAll()).thenReturn(Arrays.<Genre>asList(russianClassic, foreignClassic));
    }

    @DisplayName("Genre find all")
    @Test
    public void testGetAll() {
        List<GenreDto> genres = genreService.getAllGenres();
        assertThat(genres).hasSize(2).containsExactly(GenreDto.toDto(russianClassic), GenreDto.toDto(foreignClassic));
        assertThat(Mockito.mockingDetails(genreRepo).getInvocations().size()).isEqualTo(1);
    }
}
