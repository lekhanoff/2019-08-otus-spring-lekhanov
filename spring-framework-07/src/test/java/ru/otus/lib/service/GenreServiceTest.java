package ru.otus.lib.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.jline.reader.LineReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.MessageSource;

import ru.otus.lib.domain.Book;
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

    @Mock
    private MessageSource messageSource;
    
    @Mock
    private LineReader lineReader;
    
    private GenreServiceImpl genreService;
    
    private Genre russianClassic = Genre.builder().genreId(1000L).name("Русская классика").build();
    private Genre foreignClassic = Genre.builder().genreId(1001L).name("Зарубежная классика").build();
    private Genre fantasy = Genre.builder().genreId(1002L).name("Fantasy").build();
    
    @BeforeEach
    public void init() throws IOException {
        genreService = new GenreServiceImpl(genreRepo, bookService, messageSource, lineReader);
    }
    
    @DisplayName("Genre creation")
    @Test
    void testCreateGenre() {
        String genreName = "Russian classic";
        String enterNewGenreMessage = "Enter new genre name: ";
        
        Mockito.when(messageSource.getMessage("genre.create", null, Locale.getDefault())).thenReturn(enterNewGenreMessage);
        Mockito.when(lineReader.readLine(enterNewGenreMessage)).thenReturn(genreName);
        Mockito.when(genreRepo.save(Genre.builder().name(genreName).build())).thenReturn(russianClassic);
        
        Genre genre = genreService.createGenre();
        assertThat(genre).isEqualTo(russianClassic);
        
        assertEquals(1, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(genreRepo).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(lineReader).getInvocations().size());
    }

    @DisplayName("Genre modification")
    @Test
    void testUpdateGenre() {
        String enterNewGenreIdMessage = "Enter genre identifier for modification: ";
        String enterGenreMessage = "Enter genre name: ";
        String genreName = "Fantasy";
        Long genreId = Long.valueOf(1002);
        
        Mockito.when(messageSource.getMessage("genre.update-get-id", null, Locale.getDefault())).thenReturn(enterNewGenreIdMessage);
        Mockito.when(messageSource.getMessage("genre.update-get-name", null, Locale.getDefault())).thenReturn(enterGenreMessage);
        
        Mockito.when(lineReader.readLine(enterNewGenreIdMessage)).thenReturn(genreId.toString());
        Mockito.when(lineReader.readLine(enterGenreMessage)).thenReturn(genreName);
        Mockito.when(genreRepo.existsById(genreId)).thenReturn(true);
        Mockito.when(genreRepo.save(Genre.builder().genreId(genreId).name(genreName).build())).thenReturn(fantasy);
        
        Genre genre = genreService.updateGenre();
        assertThat(genre).isEqualTo(fantasy);
        
        assertEquals(2, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(2, Mockito.mockingDetails(lineReader).getInvocations().size());
        assertEquals(2, Mockito.mockingDetails(genreRepo).getInvocations().size());
    }

    @DisplayName("Genre deletion")
    @Test
    void testDeleteGenre() {
        String enterNewGenreIdMessage = "Enter genre identifier for deletion: ";
        Long genreId = Long.valueOf(1002);
        
        Mockito.when(messageSource.getMessage("genre.delete", null, Locale.getDefault())).thenReturn(enterNewGenreIdMessage);
        Mockito.when(messageSource.getMessage("operation.successful", null, Locale.getDefault())).thenReturn("Success");
        Mockito.when(lineReader.readLine(enterNewGenreIdMessage)).thenReturn(genreId.toString());
        Mockito.when(bookService.getBooksByGenreId(genreId)).thenReturn(Collections.<Book>emptyList());

        genreService.deleteGenre();
        
        assertEquals(2, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(lineReader).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(genreRepo).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(bookService).getInvocations().size());
    }

    @DisplayName("Genre searching by id")
    @Test
    void testGetById() {
        String enterGenreId = "Enter genre identifier: ";
        
        Mockito.when(messageSource.getMessage("genre.get-by-id", null, Locale.getDefault())).thenReturn(enterGenreId);
        Mockito.when(lineReader.readLine(enterGenreId)).thenReturn("1000");
        Mockito.when(genreRepo.findById(1000L)).thenReturn(Optional.of(russianClassic));
        
        
        Genre genre = genreService.getById().get();
        assertThat(genre).isEqualTo(russianClassic);
        
        assertEquals(1, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(genreRepo).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(lineReader).getInvocations().size());
    }

    @DisplayName("Genre searching by name")
    @Test
    void testGetByName() {
        String enterGenreName = "Enter genre name: ";
        
        Mockito.when(messageSource.getMessage("genre.get-by-name", null, Locale.getDefault())).thenReturn(enterGenreName);
        Mockito.when(lineReader.readLine(enterGenreName)).thenReturn("Russian Classic");
        Mockito.when(genreRepo.findByName("Russian Classic")).thenReturn(Optional.of(russianClassic));
        
        Genre genre = genreService.getByName().get();
        assertThat(genre).isEqualTo(russianClassic);
        
        assertEquals(1, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(genreRepo).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(lineReader).getInvocations().size());
    }

    @DisplayName("Genre get all records")
    @Test
    void testGetAll() {
        Mockito.when(genreRepo.findAll()).thenReturn(Arrays.asList(russianClassic, foreignClassic));
        List<Genre> genreList = genreService.getAll();
        assertThat(genreList).hasSize(2).containsExactly(russianClassic, foreignClassic);
        assertEquals(1, Mockito.mockingDetails(genreRepo).getInvocations().size());
    }

}
