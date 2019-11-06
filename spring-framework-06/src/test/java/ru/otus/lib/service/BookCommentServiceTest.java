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
import ru.otus.lib.domain.BookComment;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.BookCommentRepository;

@DisplayName("Testing BookCommentService")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BookCommentServiceTest {

    @Mock
    private BookCommentRepository bookCommentRepo;
    
    @Mock
    private BookService bookService;

    @Mock
    private MessageSource messageSource;
    
    @Mock
    private LineReader lineReader;
    
    private BookCommentServiceImpl bookCommentService;
    
    private Genre russianClassic = Genre.builder().genreId(Long.valueOf(100)).name("Русская классика").build();
    private Author authorLeoTolstoy = Author.builder().authorId(Long.valueOf(1000)).lastname("Толстой").firstname("Лев").middlename("Николаевич").build();
    private Book warAndPeace = Book.builder()
            .bookId(Long.valueOf(1000))
            .author(authorLeoTolstoy)
            .genre(russianClassic)
            .title("Война и мир")
            .build();
    private BookComment testComment1 = BookComment.builder()
            .bookCommentId(Long.valueOf(2000))
            .book(warAndPeace)
            .userLogin("ivanov")
            .userComment("Super book")
            .build();
    private BookComment testComment2 = BookComment.builder()
            .bookCommentId(Long.valueOf(2001))
            .book(warAndPeace)
            .userLogin("petrov")
            .userComment("Best book ever")
            .build();
    
    @BeforeEach
    public void init() throws IOException {
        bookCommentService = new BookCommentServiceImpl(bookCommentRepo, bookService, messageSource, lineReader);
    }
    
    @DisplayName("BookComment creation")
    @Test
    void testCreateBookComment() {
        String enterBookIdMessage = "Enter book id:"; 
        String enterUserNameMessage = "Enter user name:"; 
        String enterUserCommentMessage = "Enter user comment:"; 
                
        String bookId = "1000";
        String userName = "ivanov";
        String userComment = "Super book";
        
        Mockito.when(messageSource.getMessage("bookComment.enter.book-id", null, Locale.getDefault())).thenReturn(enterBookIdMessage);
        Mockito.when(messageSource.getMessage("bookComment.enter.user-name", null, Locale.getDefault())).thenReturn(enterUserNameMessage);
        Mockito.when(messageSource.getMessage("bookComment.enter.user-comment", null, Locale.getDefault())).thenReturn(enterUserCommentMessage);
        Mockito.when(lineReader.readLine(enterBookIdMessage)).thenReturn(bookId);
        Mockito.when(lineReader.readLine(enterUserNameMessage)).thenReturn(userName);
        Mockito.when(lineReader.readLine(enterUserCommentMessage)).thenReturn(userComment);
        
        Mockito.when(bookService.getById(Long.valueOf(bookId))).thenReturn(Optional.of(warAndPeace));
        Mockito.when(bookCommentRepo.getByBookAndUser(Long.valueOf(bookId), userName)).thenReturn(Optional.empty());
        Mockito.when(bookCommentRepo.createBookComment(BookComment.builder().book(warAndPeace).userLogin(userName).userComment(userComment).build())).thenReturn(testComment1);
        
        BookComment bookComment = bookCommentService.createBookComment();
        assertThat(bookComment).isEqualTo(testComment1);
        
        assertEquals(3, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(3, Mockito.mockingDetails(lineReader).getInvocations().size());
        assertEquals(2, Mockito.mockingDetails(bookCommentRepo).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(bookService).getInvocations().size());
    }

    @DisplayName("BookComment modification")
    @Test
    void testUpdateBookComment() {
        String enterBookCommentIdMessage = "Enter book comment id:"; 
        String enterBookIdMessage = "Enter book id:"; 
        String enterUserNameMessage = "Enter user name:"; 
        String enterUserCommentMessage = "Enter user comment:"; 
                
        String bookCommentId = String.valueOf(testComment1.getBookCommentId());
        String bookId = "1000";
        String userName = "ivanov";
        String userComment = "Super book";
        
        Mockito.when(messageSource.getMessage("bookComment.get-id", null, Locale.getDefault())).thenReturn(enterBookCommentIdMessage);
        Mockito.when(messageSource.getMessage("bookComment.enter.book-id", null, Locale.getDefault())).thenReturn(enterBookIdMessage);
        Mockito.when(messageSource.getMessage("bookComment.enter.user-name", null, Locale.getDefault())).thenReturn(enterUserNameMessage);
        Mockito.when(messageSource.getMessage("bookComment.enter.user-comment", null, Locale.getDefault())).thenReturn(enterUserCommentMessage);
        Mockito.when(lineReader.readLine(enterBookCommentIdMessage)).thenReturn(bookCommentId);
        Mockito.when(lineReader.readLine(enterBookIdMessage)).thenReturn(bookId);
        Mockito.when(lineReader.readLine(enterUserNameMessage)).thenReturn(userName);
        Mockito.when(lineReader.readLine(enterUserCommentMessage)).thenReturn(userComment);
        
        Mockito.when(bookService.getById(Long.valueOf(bookId))).thenReturn(Optional.of(warAndPeace));
        Mockito.when(bookCommentRepo.getById(testComment1.getBookCommentId())).thenReturn(Optional.of(testComment1));
        Mockito.when(bookCommentRepo.getByBookAndUser(Long.valueOf(bookId), userName)).thenReturn(Optional.of(testComment1));
        Mockito.when(bookCommentRepo.updateBookComment(testComment1)).thenReturn(testComment1);
        
        BookComment bookComment = bookCommentService.updateBookComment();
        assertThat(bookComment).isEqualTo(testComment1);
        
        assertEquals(4, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(4, Mockito.mockingDetails(lineReader).getInvocations().size());
        assertEquals(3, Mockito.mockingDetails(bookCommentRepo).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(bookService).getInvocations().size());
    }

    @DisplayName("BookComment deletion")
    @Test
    void testDeleteBookComment() {
        String enterNewBookCommentIdMessage = "Enter bookComment identifier for deletion: ";
        Long bookCommentId = testComment2.getBookCommentId();
        
        Mockito.when(messageSource.getMessage("bookComment.get-id", null, Locale.getDefault())).thenReturn(enterNewBookCommentIdMessage);
        Mockito.when(messageSource.getMessage("operation.successful", null, Locale.getDefault())).thenReturn("Success");
        Mockito.when(lineReader.readLine(enterNewBookCommentIdMessage)).thenReturn(bookCommentId.toString());

        bookCommentService.deleteBookComment();
        
        assertEquals(2, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(lineReader).getInvocations().size());
    }

    @DisplayName("BookComment searching by id")
    @Test
    void testGetById() {
        String enterBookCommentId = "Enter bookComment identifier: ";
        Long bookCommentId = testComment1.getBookCommentId();
        
        Mockito.when(messageSource.getMessage("bookComment.get-id", null, Locale.getDefault())).thenReturn(enterBookCommentId);
        Mockito.when(lineReader.readLine(enterBookCommentId)).thenReturn(bookCommentId.toString());
        Mockito.when(bookCommentRepo.getById(bookCommentId)).thenReturn(Optional.of(testComment1));
        
        BookComment bookComment = bookCommentService.getById().get();
        assertThat(bookComment).isEqualTo(testComment1);
        
        assertEquals(1, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(lineReader).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(bookCommentRepo).getInvocations().size());
    }

    @DisplayName("BookComment searching by book id")
    @Test
    void testGetBookCommentsByBookId() {
        String enterBookIdMessage = "Enter book id:"; 
        String bookId = String.valueOf(warAndPeace.getBookId());
        
        Mockito.when(messageSource.getMessage("bookComment.enter.book-id", null, Locale.getDefault())).thenReturn(enterBookIdMessage);
        Mockito.when(lineReader.readLine(enterBookIdMessage)).thenReturn(bookId);
        Mockito.when(bookCommentRepo.getCommentsByBookId(Long.valueOf(bookId))).thenReturn(Arrays.asList(testComment1, testComment2));
        
        List<BookComment> bookCommentList = bookCommentService.getBookCommentsByBookId();
        assertThat(bookCommentList).hasSize(2).containsExactly(testComment1, testComment2);
        
        assertEquals(1, Mockito.mockingDetails(messageSource).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(bookCommentRepo).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(lineReader).getInvocations().size());
    }

}
