package ru.otus.lib.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import ru.otus.lib.domain.Author;
import ru.otus.lib.domain.Book;
import ru.otus.lib.domain.BookComment;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.BookCommentRepositoryImpl;

@DisplayName("BookCommentRepository tests")
@DataJpaTest
@Import(BookCommentRepositoryImpl.class)
public class BookCommentRepositoryTest {
    
    @Autowired
    private BookCommentRepositoryImpl bookCommentRepo;
    
    @Autowired
    private TestEntityManager em;
    
    private Genre russianClassic = Genre.builder().name("Русская классика").build();
    private Author authorLeoTolstoy = Author.builder().lastname("Толстой").firstname("Лев").middlename("Николаевич").build();
    private Book warAndPeace = Book.builder()
            .author(authorLeoTolstoy)
            .genre(russianClassic)
            .title("Война и мир")
            .build();
    private BookComment testComment1 = BookComment.builder()
            .book(warAndPeace)
            .userLogin("ivanov")
            .userComment("Super book")
            .build();
    private BookComment testComment2 = BookComment.builder()
            .book(warAndPeace)
            .userLogin("petrov")
            .userComment("Best book ever")
            .build();
    
    @BeforeEach
    private void init() {
        em.persist(russianClassic);
        em.persist(authorLeoTolstoy);
        em.persist(warAndPeace);
        em.persist(testComment1);
        em.persist(testComment2);
    }
    
    @DisplayName("BookComment creation")
    @Test
    void testCreateBookComment() {
        BookComment bookComment = BookComment.builder().book(warAndPeace).userLogin("testUser01").userComment("This is the perfect book. I like it very much!!!").build();
        BookComment createdBookComment = bookCommentRepo.createBookComment(bookComment);
        bookComment.setBookCommentId(createdBookComment.getBookCommentId());
        assertThat(createdBookComment.getBookCommentId()).isNotNull();
        assertThat(createdBookComment).isEqualTo(bookComment);
        
    }

    @DisplayName("BookComment modification")
    @Test
    void testUpdateBookComment() {
        BookComment bookCommentForUpdate = BookComment.builder().bookCommentId(1000L).book(warAndPeace).userLogin("testUser01").userComment("This is the perfect book. I like it very much!!!").build();

        BookComment result = bookCommentRepo.updateBookComment(bookCommentForUpdate);
        assertThat(result).isEqualTo(bookCommentForUpdate);
    }

    @DisplayName("BookComment deletion")
    @Test
    void testDeleteBookComment() {
        bookCommentRepo.deleteBookComment(testComment2.getBookCommentId());
        assertThat(bookCommentRepo.getById(testComment2.getBookCommentId())).isEmpty();
    }

    @DisplayName("Search BookComment by id")
    @Test
    void testGetById() {
        Optional<BookComment> bookComment = bookCommentRepo.getById(testComment1.getBookCommentId());
        assertThat(bookComment.get()).isEqualTo(testComment1);
    }

    @DisplayName("Search BookComment by bookId and userName")
    @Test
    void testGetByBookAndUserName() {
        Optional<BookComment> bookComment = bookCommentRepo.getByBookAndUser(testComment1.getBook().getBookId(), testComment1.getUserLogin());
        assertThat(bookComment.get()).isEqualTo(testComment1);
    }

    @DisplayName("Search BookComment by bookId and userName with empty result.")
    @Test
    void testGetByBookAndUserNameEmpty() {
        Optional<BookComment> bookComment = bookCommentRepo.getByBookAndUser(Long.valueOf(800), "someLogin");
        assertThat(bookComment).isEmpty();
    }

    
    @DisplayName("Search BookComments by bookId")
    @Test
    void testGetByBookId() {
        List<BookComment> bookCommentList = bookCommentRepo.getCommentsByBookId(warAndPeace.getBookId());
        assertThat(bookCommentList).hasSize(2).containsExactly(testComment1, testComment2);
    }

}
