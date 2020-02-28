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
import ru.otus.lib.controller.dto.BookDto;
import ru.otus.lib.controller.dto.GenreDto;
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
    public void testCreateBook() {
        when(bookRepo.save(warAndPeace)).thenReturn(warAndPeace);
        BookDto book = bookService.saveBook(BookDto.toDto(warAndPeace));
        assertThat(book).isEqualTo(BookDto.toDto(warAndPeace));
        assertThat(Mockito.mockingDetails(bookRepo).getInvocations().size()).isEqualTo(1);
    }

    @DisplayName("Book delete")
    @Test
    public void testDeleteBook() {
        bookService.deleteBook(warAndPeace.getId());
        assertThat(Mockito.mockingDetails(bookRepo).getInvocations().size()).isEqualTo(1);
    }

    @DisplayName("Book find by params")
    @Test
    public void testGetBooksByParams() {
        when(bookRepo.findByParams("filter")).thenReturn(Arrays.asList(warAndPeace, annaKarenina));
        List<BookDto> books = bookService.getBooksByParams("filter");
        assertThat(books).hasSize(2).containsExactly(BookDto.toDto(warAndPeace), BookDto.toDto(annaKarenina));
        assertThat(Mockito.mockingDetails(bookRepo).getInvocations().size()).isEqualTo(1);
    }

    @DisplayName("Book find all")
    @Test
    public void testGetAllBooks() {
        when(bookRepo.findAll()).thenReturn(Arrays.asList(warAndPeace, annaKarenina));
        List<BookDto> books = bookService.getAllBooks();
        assertThat(books).hasSize(2).containsExactly(BookDto.toDto(warAndPeace), BookDto.toDto(annaKarenina));
        assertThat(Mockito.mockingDetails(bookRepo).getInvocations().size()).isEqualTo(1);
    }

    @DisplayName("Book find by id")
    @Test
    public void testGetBookById() {
        when(bookRepo.findById(warAndPeace.getId())).thenReturn(Optional.of(warAndPeace));
        Optional<BookDto> book = bookService.getBookById(warAndPeace.getId());
        assertThat(book).isEqualTo(Optional.of(BookDto.toDto(warAndPeace)));
        assertThat(Mockito.mockingDetails(bookRepo).getInvocations().size()).isEqualTo(1);
    }
    
    @DisplayName("Get books count by genreId")
    @Test
    public void testGetBooksCountByGenreId() {
        when(bookRepo.countByGenreId(russianClassic.getId())).thenReturn(1);
        assertThat(bookService.getCountByGenreId(russianClassic.getId())).isEqualTo(1);
    }
    
    @DisplayName("Get books count by authorId")
    @Test
    public void testGetBooksCountByAuthorId() {
        when(bookRepo.countByAuthorId(authorLeoTolstoy.getId())).thenReturn(1);
        assertThat(bookService.getCountByAuthorId(authorLeoTolstoy.getId())).isEqualTo(1);
    }
    
    @DisplayName("Histrix methods test")
    @Test
    public void histrixMethodsTest() {
        BookDto book = BookDto.builder()
                .id(Long.valueOf(-1))
                .author(AuthorDto.builder()
                        .id(Long.valueOf(-1))
                        .lastname("Doe")
                        .firstname("John")
                        .middlename("Jr.")
                        .build())
                .title("No name book")
                .genre(GenreDto.builder().id(Long.valueOf(-1)).name("Mystery").build())
                .build();

        assertThat(bookService.getDefaultBooks()).hasSize(1).containsExactly(book);
        assertThat(bookService.getDefaultBooksWithFilter("")).hasSize(1).containsExactly(book);
        assertThat(bookService.getDefaultBook(Long.valueOf(1)).orElse(null)).isEqualTo(book);
        assertThat(bookService.defaultSaveBook(BookDto.builder().build())).isEqualTo(book);
        bookService.defaultDeleteBook(Long.valueOf(1));
    }

}
