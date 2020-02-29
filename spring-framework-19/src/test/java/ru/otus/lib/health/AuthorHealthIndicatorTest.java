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
import ru.otus.lib.repository.AuthorRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthorHealthIndicator")
public class AuthorHealthIndicatorTest {

    @Mock
    private AuthorRepository authorRepository;
    
    private AuthorHealthIndicator authorHealthIndicator;

    @BeforeEach
    public void init() {
        authorHealthIndicator = new AuthorHealthIndicator(authorRepository);
    }
    
    @Test
    public void testHealth() {
        when(authorRepository.findAll()).thenReturn(Arrays.asList(Author.builder()
                .id(Long.valueOf(1000))
                .lastname("Ivanov")
                .firstname("Ivan")
                .middlename("Ivanovich")
                .build()));
        assertThat(authorHealthIndicator.health().getStatus()).isEqualTo(Status.UP);
    }

    @Test
    public void testHealthDown() {
        when(authorRepository.findAll()).thenReturn(Arrays.asList());
        assertThat(authorHealthIndicator.health().getStatus()).isEqualTo(Status.DOWN);
    }

}
