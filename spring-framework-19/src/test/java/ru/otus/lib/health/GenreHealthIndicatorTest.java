package ru.otus.lib.health;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Status;

import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.GenreRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("GenreHealthIndicator")
public class GenreHealthIndicatorTest {

    @Mock
    private GenreRepository genreRepository;
    
    private GenresHealthIndicator genresHealthIndicator;
    
    @BeforeEach
    public void init() {
        genresHealthIndicator = new GenresHealthIndicator(genreRepository);
    }

    @Test
    public void testHealth() {
        when(genreRepository.findAll()).thenReturn(Arrays.asList(Genre.builder().id(Long.valueOf(1000)).name("Русская классика").build()));
        assertThat(genresHealthIndicator.health().getStatus()).isEqualTo(Status.UP);
    }

    @Test
    public void testHealthDown() {
        when(genreRepository.findAll()).thenReturn(Arrays.asList());
        assertThat(genresHealthIndicator.health().getStatus()).isEqualTo(Status.DOWN);
    }

}
