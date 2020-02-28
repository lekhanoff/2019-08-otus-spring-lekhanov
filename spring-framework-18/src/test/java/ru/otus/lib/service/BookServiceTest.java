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

import ru.otus.lib.controller.dto.BookDto;
import ru.otus.lib.domain.Author;
import ru.otus.lib.domain.Book;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.BookRepository;

@DisplayName("Testing BookService")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepo;

    private BookServiceImpl bookService;
    
    private Author authorLeoTolstoy = Author.builder().id(Long.valueOf(1000)).lastname("Толстой").firstname("Лев").middlename("Николаевич").build();
    
    private Genre russianClassic = Genre.builder().id(Long.valueOf(1000)).name("Русская классика").build();
    
    private Book warAndPeace = Book.builder()
            .id(Long.valueOf(1000))
            .author(authorLeoTolstoy)
            .genre(russianClassic)
            .title("Война и мир")
            .build();
    private Book annaKarenina = Book.builder()
            .id(Long.valueOf(1000))
            .author(authorLeoTolstoy)
            .genre(russianClassic)
            .title("Анна Каренина")
            .build();

    @BeforeEach
    public void init() throws IOException {
        bookService = new BookServiceImpl(bookRepo);
    }
    
    @DisplayName("Book save")
    @Test
    void testCreateBook() {
        when(bookRepo.save(warAndPeace)).thenReturn(warAndPeace);
        BookDto book = bookService.saveBook(BookDto.toDto(warAndPeace));
        assertThat(book).isEqualTo(BookDto.toDto(warAndPeace));
        assertThat(Mockito.mockingDetails(bookRepo).getInvocations().size()).isEqualTo(1);
    }

    @DisplayName("Book delete")
    @Test
    void testDeleteBook() {
        bookService.deleteBook(warAndPeace.getId());
        assertThat(Mockito.mockingDetails(bookRepo).getInvocations().size()).isEqualTo(1);
    }

    @DisplayName("Book find by params")
    @Test
    void testGetBooksByParams() {
        when(bookRepo.findByParams("filter")).thenReturn(Arrays.asList(warAndPeace, annaKarenina));
        List<BookDto> books = bookService.getBooksByParams("filter");
        assertThat(books).hasSize(2).containsExactly(BookDto.toDto(warAndPeace), BookDto.toDto(annaKarenina));
        assertThat(Mockito.mockingDetails(bookRepo).getInvocations().size()).isEqualTo(1);
    }

    @DisplayName("Book find all")
    @Test
    void testGetAllBooks() {
        when(bookRepo.findAll()).thenReturn(Arrays.asList(warAndPeace, annaKarenina));
        List<BookDto> books = bookService.getAllBooks();
        assertThat(books).hasSize(2).containsExactly(BookDto.toDto(warAndPeace), BookDto.toDto(annaKarenina));
        assertThat(Mockito.mockingDetails(bookRepo).getInvocations().size()).isEqualTo(1);
    }

    @DisplayName("Book find by id")
    @Test
    void testGetBookById() {
        when(bookRepo.findById(warAndPeace.getId())).thenReturn(Optional.of(warAndPeace));
        Optional<BookDto> book = bookService.getBookById(warAndPeace.getId());
        assertThat(book).isEqualTo(Optional.of(BookDto.toDto(warAndPeace)));
        assertThat(Mockito.mockingDetails(bookRepo).getInvocations().size()).isEqualTo(1);
    }
}
