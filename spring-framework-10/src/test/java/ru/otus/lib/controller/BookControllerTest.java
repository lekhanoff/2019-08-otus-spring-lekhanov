package ru.otus.lib.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.otus.lib.controller.dto.AuthorDto;
import ru.otus.lib.controller.dto.BookDto;
import ru.otus.lib.controller.dto.GenreDto;
import ru.otus.lib.service.BookService;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService service;

    private AuthorDto authorLeoTolstoy = AuthorDto.builder()
            .id(Long.valueOf(1000))
            .lastname("Толстой")
            .firstname("Лев")
            .middlename("Николаевич")
            .build();

    private GenreDto russianClassic = GenreDto.builder().id(Long.valueOf(1000)).name("Русская классика").build();

    private BookDto warAndPeace = BookDto.builder()
            .id(Long.valueOf(1000))
            .author(authorLeoTolstoy)
            .genre(russianClassic)
            .title("Война и мир")
            .build();
    private BookDto annaKarenina = BookDto.builder()
            .id(Long.valueOf(1000))
            .author(authorLeoTolstoy)
            .genre(russianClassic)
            .title("Анна Каренина")
            .build();

    @Test
    public void testSaveBook() throws Exception {
        when(service.saveBook(warAndPeace)).thenReturn(warAndPeace);
        mockMvc.perform(post("/v1/books").content(new ObjectMapper().writeValueAsBytes(warAndPeace))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(warAndPeace.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(warAndPeace.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(warAndPeace.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(warAndPeace.getGenre()));

    }

    @Test
    public void testGetAllBooks() throws Exception {
        when(service.getAllBooks()).thenReturn(Arrays.asList(warAndPeace, annaKarenina));
        mockMvc.perform(get("/v1/books"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").value(warAndPeace))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1]").value(annaKarenina));
    }

    @Test
    public void testGetBookById() throws Exception {
        Long bookId = Long.valueOf(1000);
        when(service.getBookById(bookId)).thenReturn(Optional.of(warAndPeace));
        mockMvc.perform(get("/v1/books/" + bookId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(warAndPeace.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(warAndPeace.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(warAndPeace.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(warAndPeace.getGenre()));
    }

}
