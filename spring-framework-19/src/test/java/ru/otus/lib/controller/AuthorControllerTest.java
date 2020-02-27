package ru.otus.lib.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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
import ru.otus.lib.service.AuthorService;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService service;

    private AuthorDto ivanov = AuthorDto.builder()
            .id(Long.valueOf(1000))
            .lastname("Ivanov")
            .firstname("Ivan")
            .middlename("Ivanovich")
            .build();
    private AuthorDto petrov = AuthorDto.builder()
            .id(Long.valueOf(1001))
            .lastname("Petrov")
            .firstname("Petr")
            .middlename("Petrovich")
            .build();

    @BeforeEach
    public void init() throws Exception {
        when(service.getAllAuthors()).thenReturn(Arrays.asList(ivanov, petrov));
    }

    @Test
    public void testFindAllAuthors() throws Exception {
        mockMvc.perform(get("/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").value(ivanov))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1]").value(petrov));
    }
    
    @Test
    public void testSaveAuthor() throws Exception {
        when(service.saveAuthor(ivanov)).thenReturn(ivanov);
        mockMvc.perform(post("/v1/authors").content(new ObjectMapper().writeValueAsBytes(ivanov))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(ivanov.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(ivanov.getLastname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(ivanov.getFirstname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.middlename").value(ivanov.getMiddlename()));
    }
    
    @Test
    public void testGetAuthorById() throws Exception {
        Long authorId = Long.valueOf(1000);
        when(service.getAuthorById(authorId)).thenReturn(Optional.of(ivanov));
        mockMvc.perform(get("/v1/authors/" + authorId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(ivanov.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(ivanov.getLastname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(ivanov.getFirstname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.middlename").value(ivanov.getMiddlename()));
    }
}
