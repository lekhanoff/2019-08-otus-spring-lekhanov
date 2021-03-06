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

import ru.otus.lib.controller.dto.AuthorDto;
import ru.otus.lib.domain.Author;
import ru.otus.lib.repository.AuthorRepository;

@DisplayName("Testing AuthorService")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepo;

    @Mock
    private BookService bookService;

    private AuthorServiceImpl authorService;
    
    private Author ivanov = Author.builder().id(Long.valueOf(1000)).lastname("Ivanov").firstname("Ivan").middlename("Ivanovich").build();
    private Author petrov = Author.builder().id(Long.valueOf(1001)).lastname("Petrov").firstname("Petr").middlename("Petrovich").build();

    @BeforeEach
    public void init() throws IOException {
        authorService = new AuthorServiceImpl(authorRepo, bookService);
        when(authorRepo.findAll()).thenReturn(Arrays.<Author>asList(ivanov, petrov));
        when(bookService.getCountByAuthorId(ivanov.getId())).thenReturn(0);
    }
    
    @DisplayName("Author find all")
    @Test
    public void testGetAll() {
        List<AuthorDto> authors = authorService.getAllAuthors();
        assertThat(authors).hasSize(2).containsExactly(AuthorDto.toDto(ivanov), AuthorDto.toDto(petrov));
        assertThat(Mockito.mockingDetails(authorRepo).getInvocations().size()).isEqualTo(1);
    }
    
    @DisplayName("Author save")
    @Test
    public void testCreateAuthor() {
        when(authorRepo.save(ivanov)).thenReturn(ivanov);
        AuthorDto author = authorService.saveAuthor(AuthorDto.toDto(ivanov));
        assertThat(author).isEqualTo(AuthorDto.toDto(ivanov));
        assertThat(Mockito.mockingDetails(authorRepo).getInvocations().size()).isEqualTo(1);
    }

    @DisplayName("Author delete")
    @Test
    public void testDeleteAuthor() {
        authorService.deleteAuthor(ivanov.getId());
        assertThat(Mockito.mockingDetails(authorRepo).getInvocations().size()).isEqualTo(1);
    }

    @DisplayName("Author find by params")
    @Test
    public void testGetAuthorsByParams() {
        when(authorRepo.findByParams("filter")).thenReturn(Arrays.asList(ivanov, petrov));
        List<AuthorDto> authors = authorService.getAuthorsByParams("filter");
        assertThat(authors).hasSize(2).containsExactly(AuthorDto.toDto(ivanov), AuthorDto.toDto(petrov));
        assertThat(Mockito.mockingDetails(authorRepo).getInvocations().size()).isEqualTo(1);
    }

    @DisplayName("Author find by id")
    @Test
    public void testGetAuthorById() {
        when(authorRepo.findById(ivanov.getId())).thenReturn(Optional.of(ivanov));
        Optional<AuthorDto> author = authorService.getAuthorById(ivanov.getId());
        assertThat(author).isEqualTo(Optional.of(AuthorDto.toDto(ivanov)));
        assertThat(Mockito.mockingDetails(authorRepo).getInvocations().size()).isEqualTo(1);
    }
    
    @DisplayName("Histrix methods test")
    @Test
    public void histrixMethodsTest() {
        AuthorDto author = AuthorDto.builder()
                .id(Long.valueOf(-1))
                .lastname("Doe")
                .firstname("John")
                .middlename("Jr.")
                .build();
        
        assertThat(authorService.getDefaultAuthors()).hasSize(1)
                .containsExactly(author);
        assertThat(authorService.getDefaultAuthor(Long.valueOf(1)).orElse(null)).isEqualTo(
                author);
        assertThat(authorService.defaultSaveAuthor(AuthorDto.builder().build())).isEqualTo(
                author);
        authorService.getDefaultDelete(Long.valueOf(1));
    }
}
