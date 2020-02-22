package ru.otus.lib.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    
}
