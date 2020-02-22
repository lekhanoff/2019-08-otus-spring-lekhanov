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

import ru.otus.lib.controller.dto.AuthorDto;
import ru.otus.lib.domain.Author;
import ru.otus.lib.repository.AuthorRepository;

@DisplayName("Testing AuthorService")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepo;

    private AuthorServiceImpl authorService;
    
    private Author ivanov = Author.builder().id(Long.valueOf(1000)).lastname("Ivanov").firstname("Ivan").middlename("Ivanovich").build();
    private Author petrov = Author.builder().id(Long.valueOf(1001)).lastname("Petrov").firstname("Petr").middlename("Petrovich").build();

    @BeforeEach
    public void init() throws IOException {
        authorService = new AuthorServiceImpl(authorRepo);
        when(authorRepo.findAll()).thenReturn(Arrays.<Author>asList(ivanov, petrov));
    }
    
    @DisplayName("Author find all")
    @Test
    public void testGetAll() {
        List<AuthorDto> authors = authorService.getAllAuthors();
        assertThat(authors).hasSize(2).containsExactly(AuthorDto.toDto(ivanov), AuthorDto.toDto(petrov));
        assertThat(Mockito.mockingDetails(authorRepo).getInvocations().size()).isEqualTo(1);
    }
}
