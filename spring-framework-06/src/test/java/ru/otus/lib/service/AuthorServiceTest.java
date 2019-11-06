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

import ru.otus.lib.domain.Author;
import ru.otus.lib.domain.Book;
import ru.otus.lib.repository.AuthorRepository;

@DisplayName("Testing AuthorService")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepo;

    @Mock
    private BookService bookService;

    @Mock
    private MessageSource messageSource;
    
    @Mock
    private LineReader lineReader;
    
    private AuthorServiceImpl authorService;
    
    private Author ivanov = Author.builder().authorId(1000L).lastname("Ivanov").firstname("Ivan").middlename("Ivanovich").build();
    private Author petrov = Author.builder().authorId(1001L).lastname("Petrov").firstname("Petr").middlename("Petrovich").build();

    @BeforeEach
    public void init() throws IOException {
        authorService = new AuthorServiceImpl(authorRepo, bookService, messageSource, lineReader);
    }
    
    @DisplayName("Author creation")
    @Test
    void testCreateAuthor() {
        String lastnameMessage = "Enter author lastname: ";
        String firstnameMessage = "Enter author firstname: ";
        String middlenameMessage = "Enter author middlename: ";
        String lastname = "Ivanov";
        String firstname = "Ivan";
        String middlename = "Ivanovich";
        
        Mockito.when(messageSource.getMessage("author.enter.lastname", null, Locale.getDefault())).thenReturn(lastnameMessage);
        Mockito.when(messageSource.getMessage("author.enter.firstname", null, Locale.getDefault())).thenReturn(firstnameMessage);
        Mockito.when(messageSource.getMessage("author.enter.middlename", null, Locale.getDefault())).thenReturn(middlenameMessage);
        
        Mockito.when(lineReader.readLine(lastnameMessage)).thenReturn(lastname);
        Mockito.when(lineReader.readLine(firstnameMessage)).thenReturn(firstname);
        Mockito.when(lineReader.readLine(middlenameMessage)).thenReturn(middlename);
        
        Mockito.when(authorRepo
                .createAuthor(Author.builder().lastname(lastname).firstname(firstname).middlename(middlename).build()))
                .thenReturn(ivanov);
        
        Author author = authorService.createAuthor();
        assertThat(author).isEqualTo(ivanov);
        
        assertEquals(3, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(3, Mockito.mockingDetails(lineReader).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(authorRepo).getInvocations().size());
    }

    @DisplayName("Author modification")
    @Test
    void testUpdateAuthor() {
        String authorIdMessage = "Enter author id";
        String lastnameMessage = "Enter author lastname: ";
        String firstnameMessage = "Enter author firstname: ";
        String middlenameMessage = "Enter author middlename: ";
        String id = "1000";
        String lastname = "Ivanov";
        String firstname = "Ivan";
        String middlename = "Ivanovich";
        
        Mockito.when(messageSource.getMessage("author.update.get-id", null, Locale.getDefault())).thenReturn(authorIdMessage);
        Mockito.when(messageSource.getMessage("author.update.lastname", null, Locale.getDefault())).thenReturn(lastnameMessage);
        Mockito.when(messageSource.getMessage("author.update.firstname", null, Locale.getDefault())).thenReturn(firstnameMessage);
        Mockito.when(messageSource.getMessage("author.update.middlename", null, Locale.getDefault())).thenReturn(middlenameMessage);
        
        Mockito.when(lineReader.readLine(authorIdMessage)).thenReturn(id);
        Mockito.when(lineReader.readLine(lastnameMessage)).thenReturn(lastname);
        Mockito.when(lineReader.readLine(firstnameMessage)).thenReturn(firstname);
        Mockito.when(lineReader.readLine(middlenameMessage)).thenReturn(middlename);
        
        Mockito.when(authorRepo
                .getById(Long.valueOf(id)))
                .thenReturn(Optional.of(ivanov));
        Mockito.when(authorRepo
                .updateAuthor(Author.builder().authorId(Long.valueOf(id)).lastname(lastname).firstname(firstname).middlename(middlename).build()))
                .thenReturn(ivanov);
        
        Author author = authorService.updateAuthor();
        assertThat(author).isEqualTo(ivanov);
        
        assertEquals(4, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(4, Mockito.mockingDetails(lineReader).getInvocations().size());
        assertEquals(2, Mockito.mockingDetails(authorRepo).getInvocations().size());
    }

    @DisplayName("Author deletion")
    @Test
    void testDeleteAuthor() {
        String enterNewGenreIdMessage = "Enter author identifier for deletion: ";
        Long authorId = Long.valueOf(1002);
        
        Mockito.when(messageSource.getMessage("author.delete", null, Locale.getDefault())).thenReturn(enterNewGenreIdMessage);
        Mockito.when(messageSource.getMessage("operation.successful", null, Locale.getDefault())).thenReturn("Success");
        Mockito.when(lineReader.readLine(enterNewGenreIdMessage)).thenReturn(authorId.toString());
        Mockito.when(bookService.getBooksByAuthorId(authorId)).thenReturn(Collections.<Book>emptyList());
        
        authorService.deleteAuthor();
        
        assertEquals(2, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(lineReader).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(bookService).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(authorRepo).getInvocations().size());
    }

    @DisplayName("Author search by id")
    @Test
    void testGetById() {
        String enterAuthorId = "Enter author identifier: ";
        
        Mockito.when(messageSource.getMessage("author.get-by-id", null, Locale.getDefault())).thenReturn(enterAuthorId);
        Mockito.when(lineReader.readLine(enterAuthorId)).thenReturn("1000");
        Mockito.when(authorRepo.getById(1000L)).thenReturn(Optional.ofNullable(ivanov));
        
        
        Optional<Author> author = authorService.getById();
        assertThat(author.get()).isEqualTo(ivanov);
        
        assertEquals(1, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(authorRepo).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(lineReader).getInvocations().size());
    }

    @DisplayName("Author find all")
    @Test
    void testGetAll() {
        Mockito.when(authorRepo.getAll()).thenReturn(Arrays.asList(ivanov, petrov));
        List<Author> authorList = authorService.getAll();
        assertThat(authorList).hasSize(2).containsExactly(ivanov, petrov);
        assertEquals(1, Mockito.mockingDetails(authorRepo).getInvocations().size());
    }

}
