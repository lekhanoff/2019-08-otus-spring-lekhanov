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

import ru.otus.lib.dao.BookDao;
import ru.otus.lib.domain.Book;

@DisplayName("Testing BookService")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BookServiceTest {

    @Mock
    private BookDao bookDao;

    @Mock
    private AuthorService authorService;

    @Mock
    private GenreService genreService;
    
    @Mock
    private MessageSource messageSource;
    
    @Mock
    private LineReader lineReader;
    
    private BookServiceImpl bookService;

    private Book bookWarAndPeace = Book.builder().bookId(100L).authorId(1000L).genreId(10000L).title("War and peace").build();
    private Book bookAnnaKarenina = Book.builder().bookId(101L).authorId(1000L).genreId(10000L).title("Anna Karenina").build();
    
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
                .createBook(Book.builder().authorId(Long.valueOf(authorId)).genreId(Long.valueOf(genreId)).title(title).build()))
                .thenReturn(bookWarAndPeace);
        Mockito.when(authorService.checkAuthorExists(Long.valueOf(authorId))).thenReturn(true);
        Mockito.when(genreService.checkGenreExists(Long.valueOf(authorId))).thenReturn(true);

        
        Book book = bookService.createBook();
        assertThat(book).isEqualTo(bookWarAndPeace);
        
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
                .getById(Long.valueOf(bookId)))
                .thenReturn(Optional.of(bookWarAndPeace));
        Mockito.when(bookDao
                .updateBook(Long.valueOf(bookId), Book.builder().authorId(Long.valueOf(authorId)).genreId(Long.valueOf(genreId)).title(title).build()))
                .thenReturn(bookWarAndPeace);
        Mockito.when(authorService.checkAuthorExists(Long.valueOf(authorId))).thenReturn(true);
        Mockito.when(genreService.checkGenreExists(Long.valueOf(authorId))).thenReturn(true);
        
        Book book = bookService.updateBook();
        assertThat(book).isEqualTo(bookWarAndPeace);
        
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
        Mockito.when(bookDao.getById(1000L)).thenReturn(Optional.ofNullable(bookWarAndPeace));
        
        Book book = bookService.getById().get();
        assertThat(book).isEqualTo(bookWarAndPeace);
        
        assertEquals(1, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(bookDao).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(lineReader).getInvocations().size());
    }

    @DisplayName("Find all books")
    @Test
    void testGetAll() {
        Mockito.when(bookDao.getAll()).thenReturn(Arrays.asList(bookWarAndPeace, bookAnnaKarenina));
        List<Book> bookList = bookService.getAll();
        assertThat(bookList).hasSize(2).containsExactly(bookWarAndPeace, bookAnnaKarenina);
        assertEquals(1, Mockito.mockingDetails(bookDao).getInvocations().size());
    }

    @DisplayName("Find books by authorId")
    @Test
    void testGetBooksByAuthorId() {
        String enterAuthorId = "Enter author identifier: ";
        String authorId = "1000";
        
        Mockito.when(messageSource.getMessage("book.get-by-author-id", null, Locale.getDefault())).thenReturn(enterAuthorId);
        Mockito.when(lineReader.readLine(enterAuthorId)).thenReturn(authorId);
        Mockito.when(bookDao.getBooksByAuthorId(Long.valueOf(authorId))).thenReturn(Arrays.asList(bookWarAndPeace, bookAnnaKarenina));
        
        List<Book> bookList = bookService.getBooksByAuthorId();
        assertThat(bookList).hasSize(2).containsExactly(bookWarAndPeace, bookAnnaKarenina);
        
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
        Mockito.when(bookDao.getBooksByGenreId(Long.valueOf(genreId))).thenReturn(Arrays.asList(bookWarAndPeace, bookAnnaKarenina));
        
        List<Book> bookList = bookService.getBooksByGenreId();
        assertThat(bookList).hasSize(2).containsExactly(bookWarAndPeace, bookAnnaKarenina);
        
        assertEquals(1, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(lineReader).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(bookDao).getInvocations().size());
    }

}
