package ru.otus.lib.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
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
import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.BookRepository;

@DisplayName("Testing BookService")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BookServiceTest {

    @Mock
    private BookRepository bookDao;

    @Mock
    private AuthorService authorService;

    @Mock
    private GenreService genreService;
    
    @Mock
    private MessageSource messageSource;
    
    @Mock
    private LineReader lineReader;
    
    private BookServiceImpl bookService;

    private Author authorLeoTolstoy = Author.builder().id("1000").lastname("Толстой").firstname("Лев").middlename("Николаевич").build();
    
    private Genre russianClassic = Genre.builder().id("1000").name("Русская классика").build();
    
    private Book warAndPeace = Book.builder()
            .id("1000")
            .author(authorLeoTolstoy)
            .genre(russianClassic)
            .title("Война и мир")
            .build();
    private Book annaKarenina = Book.builder()
            .id("1001")
            .author(authorLeoTolstoy)
            .genre(russianClassic)
            .title("Анна Каренина")
            .build();
    
    @BeforeEach
    public void init() throws IOException {
        bookService = new BookServiceImpl(bookDao, authorService, genreService, messageSource, lineReader);
    }
    
    @DisplayName("Book creation")
    @Test
    void testCreateBook() {
        String authorMessage = "Enter book's authorId: ";
        String genreMessage = "Enter book's genreId: ";
        String titleMessage = "Enter book's title: ";
        String authorId = "1000";
        String genreId = "100";
        String title = "War and peace";
        
        Mockito.when(messageSource.getMessage("book.enter.author-id", null, Locale.getDefault())).thenReturn(authorMessage);
        Mockito.when(messageSource.getMessage("book.enter.genre-id", null, Locale.getDefault())).thenReturn(genreMessage);
        Mockito.when(messageSource.getMessage("book.enter.title", null, Locale.getDefault())).thenReturn(titleMessage);
        
        Mockito.when(lineReader.readLine(authorMessage)).thenReturn(authorId);
        Mockito.when(lineReader.readLine(genreMessage)).thenReturn(genreId);
        Mockito.when(lineReader.readLine(titleMessage)).thenReturn(title);
        
        Mockito.when(bookDao
                .save(Book.builder().author(authorLeoTolstoy).genre(russianClassic).title(title).build()))
                .thenReturn(warAndPeace);
        Mockito.when(authorService.getById(authorId)).thenReturn(Optional.of(authorLeoTolstoy));
        Mockito.when(genreService.getById(genreId)).thenReturn(Optional.of(russianClassic));
        
        Book book = bookService.createBook();
        assertThat(book).isEqualTo(warAndPeace);
        
        assertEquals(3, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(3, Mockito.mockingDetails(lineReader).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(authorService).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(genreService).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(bookDao).getInvocations().size());
    }

    @DisplayName("Book modification")
    @Test
    void testUpdateBook() {
        String bookIdMessage = "Enter book id ";
        String authorMessage = "Enter book's authorId: ";
        String genreMessage = "Enter book's genreId: ";
        String titleMessage = "Enter book's title: ";
        
        String bookId = "100";
        String authorId = "1000";
        String genreId = "100";
        String title = "War and peace";
        
        Mockito.when(messageSource.getMessage("book.update.get-id", null, Locale.getDefault())).thenReturn(bookIdMessage);
        Mockito.when(messageSource.getMessage("book.update.author-id", null, Locale.getDefault())).thenReturn(authorMessage);
        Mockito.when(messageSource.getMessage("book.update.genre-id", null, Locale.getDefault())).thenReturn(genreMessage);
        Mockito.when(messageSource.getMessage("book.update.title", null, Locale.getDefault())).thenReturn(titleMessage);
        
        Mockito.when(lineReader.readLine(bookIdMessage)).thenReturn(bookId);
        Mockito.when(lineReader.readLine(authorMessage)).thenReturn(authorId);
        Mockito.when(lineReader.readLine(genreMessage)).thenReturn(genreId);
        Mockito.when(lineReader.readLine(titleMessage)).thenReturn(title);
        
        Mockito.when(bookDao
                .existsById(bookId))
                .thenReturn(true);
        Mockito.when(bookDao
                .save(Book.builder().id(bookId).author(authorLeoTolstoy).genre(russianClassic).title(title).build()))
                .thenReturn(warAndPeace);
        Mockito.when(authorService.getById(authorId)).thenReturn(Optional.of(authorLeoTolstoy));
        Mockito.when(genreService.getById(genreId)).thenReturn(Optional.of(russianClassic));
        
        Book book = bookService.updateBook();
        assertThat(book).isEqualTo(warAndPeace);
        
        assertEquals(4, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(4, Mockito.mockingDetails(lineReader).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(authorService).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(genreService).getInvocations().size());
        assertEquals(2, Mockito.mockingDetails(bookDao).getInvocations().size());
    }

    @DisplayName("Book deletion")
    @Test
    void testDeleteBook() {
        String enterNewBookIdMessage = "Enter book identifier for deletion: ";
        Long bookId = Long.valueOf(1002);
        
        Mockito.when(messageSource.getMessage("book.delete", null, Locale.getDefault())).thenReturn(enterNewBookIdMessage);
        Mockito.when(messageSource.getMessage("operation.successful", null, Locale.getDefault())).thenReturn("Success");
        Mockito.when(lineReader.readLine(enterNewBookIdMessage)).thenReturn(bookId.toString());

        bookService.deleteBook();
        
        assertEquals(2, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(lineReader).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(bookDao).getInvocations().size());
    }

    @DisplayName("Book search by id")
    @Test
    void testGetById() {
        String enterBookId = "Enter book identifier: ";
        
        Mockito.when(messageSource.getMessage("book.get-by-id", null, Locale.getDefault())).thenReturn(enterBookId);
        Mockito.when(lineReader.readLine(enterBookId)).thenReturn("1000");
        Mockito.when(bookDao.findById("1000")).thenReturn(Optional.ofNullable(warAndPeace));
        
        Book book = bookService.getById().get();
        assertThat(book).isEqualTo(warAndPeace);
        
        assertEquals(1, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(bookDao).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(lineReader).getInvocations().size());
    }

    @DisplayName("Find all books")
    @Test
    void testGetAll() {
        Mockito.when(bookDao.findAll()).thenReturn(Arrays.asList(warAndPeace, annaKarenina));
        List<Book> bookList = bookService.getAll();
        assertThat(bookList).hasSize(2).containsExactly(warAndPeace, annaKarenina);
        assertEquals(1, Mockito.mockingDetails(bookDao).getInvocations().size());
    }

    @DisplayName("Find books by authorId")
    @Test
    void testGetBooksByAuthorId() {
        String enterAuthorId = "Enter author identifier: ";
        String authorId = "1000";
        
        Mockito.when(messageSource.getMessage("book.get-by-author-id", null, Locale.getDefault())).thenReturn(enterAuthorId);
        Mockito.when(lineReader.readLine(enterAuthorId)).thenReturn(authorId);
        Mockito.when(bookDao.findByAuthorId(authorId)).thenReturn(Arrays.asList(warAndPeace, annaKarenina));
        
        List<Book> bookList = bookService.getBooksByAuthorId();
        assertThat(bookList).hasSize(2).containsExactly(warAndPeace, annaKarenina);
        
        assertEquals(1, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(lineReader).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(bookDao).getInvocations().size());
    }

    @DisplayName("Find books by genreId")
    @Test
    void testGetBooksByGenreId() {
        String enterGenreId = "Enter genre identifier: ";
        String genreId = "10000";
        
        Mockito.when(messageSource.getMessage("book.get-by-genre-id", null, Locale.getDefault())).thenReturn(enterGenreId);
        Mockito.when(lineReader.readLine(enterGenreId)).thenReturn(genreId);
        Mockito.when(bookDao.findByGenreId(genreId)).thenReturn(Arrays.asList(warAndPeace, annaKarenina));
        
        List<Book> bookList = bookService.getBooksByGenreId();
        assertThat(bookList).hasSize(2).containsExactly(warAndPeace, annaKarenina);
        
        assertEquals(1, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(lineReader).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(bookDao).getInvocations().size());
    }

}
