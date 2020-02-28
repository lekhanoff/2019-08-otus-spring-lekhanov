package ru.otus.lib.health;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Status;

import ru.otus.lib.domain.Author;
import ru.otus.lib.domain.Book;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookHealthIndicator")
public class BookHealthIndicatorTest {

    @Mock
    private BookRepository bookRepository;
    
    private BooksHealthIndicator booksHealthIndicator;
    
    private Author authorLeoTolstoy = Author.builder()
            .id(Long.valueOf(1000))
            .lastname("Толстой")
            .firstname("Лев")
            .middlename("Николаевич")
            .build();

    private Genre russianClassic = Genre.builder().id(Long.valueOf(1000)).name("Русская классика").build();

    private Book warAndPeace = Book.builder()
            .id(Long.valueOf(1000))
            .author(authorLeoTolstoy)
            .genre(russianClassic)
            .title("Война и мир")
            .build();

    @BeforeEach
    public void init() {
        booksHealthIndicator = new BooksHealthIndicator(bookRepository);
    }

    @Test
    public void testHealth() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(warAndPeace));
        assertThat(booksHealthIndicator.health().getStatus()).isEqualTo(Status.UP);
    }

    @Test
    public void testHealthDown() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList());
        assertThat(booksHealthIndicator.health().getStatus()).isEqualTo(Status.DOWN);
    }
}
