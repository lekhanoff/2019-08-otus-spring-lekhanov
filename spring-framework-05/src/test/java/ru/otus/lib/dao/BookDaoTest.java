package ru.otus.lib.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import ru.otus.lib.domain.Author;
import ru.otus.lib.domain.Book;
import ru.otus.lib.domain.Genre;

@DisplayName("BookDao tests")
@JdbcTest
@Import(BookDaoImpl.class)
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class
})
@DatabaseSetup({"author-dataset.xml", "book-dataset.xml", "genre-dataset.xml"})
public class BookDaoTest {

    private Book warAndPeace = Book.builder()
            .bookId(1000L)
            .authorId(1000L)
            .genreId(1000L)
            .title("Война и мир")
            .author(Author.builder()
                    .authorId(1000L)
                    .lastname("Толстой")
                    .firstname("Лев")
                    .middlename("Николаевич")
                    .build())
            .genre(Genre.builder().genreId(1000L).name("Русская классика").build())
            .build();
    private Book annaKarenina = Book.builder()
            .bookId(1001L)
            .authorId(1000L)
            .genreId(1000L)
            .title("Анна Каренина")
            .author(Author.builder()
                    .authorId(1000L)
                    .lastname("Толстой")
                    .firstname("Лев")
                    .middlename("Николаевич")
                    .build())
            .genre(Genre.builder().genreId(1000L).name("Русская классика").build())
            .build();
    
    @Autowired
    private BookDaoImpl bookDao;
    
    @DisplayName("Book creation")
    @Test
    void testCreateBook() {
        Book voskresenie = Book.builder().authorId(1000L).genreId(1000L).title("Воскресенье").build();
        
        Book book = bookDao.createBook(voskresenie);
        voskresenie.setBookId(book.getBookId());
        
        assertThat(book).isEqualTo(voskresenie);
    }

    @DisplayName("Book modification")
    @Test
    void testUpdateBook() {
        Long bookId = Long.valueOf(1000);
        Book bookForModify = Book.builder().bookId(bookId).authorId(1000L).genreId(1001L).title("ВОЙНА И МИР").build();
        
        Book book = bookDao.updateBook(bookId, bookForModify);
        assertThat(book).isEqualTo(bookForModify);
    }

    @DisplayName("Book modification")
    @Test
    void testDeleteBook() {
        Long bookId = Long.valueOf(1001);
        bookDao.deleteBook(bookId);
        assertThatExceptionOfType(IncorrectResultSizeDataAccessException.class)
                .isThrownBy(() -> bookDao.getById(bookId));
    }

    @DisplayName("Search book by id")
    @Test
    void testGetById() {
        Optional<Book> book = bookDao.getById(Long.valueOf(1000));
        assertThat(book.get()).isEqualTo(warAndPeace);
    }

    @DisplayName("Search of all books")
    @Test
    void testGetAll() {
        List<Book> bookList = bookDao.getAll();
        assertThat(bookList).hasSize(2).containsExactly(warAndPeace, annaKarenina);
    }

    @DisplayName("Search books by author id")
    @Test
    void testGetBooksByAuthorId() {
        List<Book> bookList = bookDao.getBooksByAuthorId(Long.valueOf(1000));
        assertThat(bookList).hasSize(2).containsExactly(warAndPeace, annaKarenina);
    }

    @DisplayName("Search books by genre id")
    @Test
    void testGetBooksByGenreId() {
        List<Book> bookList = bookDao.getBooksByGenreId(Long.valueOf(1000));
        assertThat(bookList).hasSize(2).containsExactly(warAndPeace, annaKarenina);
    }

}
