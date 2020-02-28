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

import ru.otus.lib.controller.dto.GenreDto;
import ru.otus.lib.service.GenreService;

@SpringBootTest
@AutoConfigureMockMvc
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService service;

    private GenreDto russianClassic = GenreDto.builder().id(Long.valueOf(1000)).name("Russian classic").build();
    private GenreDto foreignClassic = GenreDto.builder().id(Long.valueOf(1001)).name("Foreign classic").build();

    @BeforeEach
    public void init() throws Exception {
        when(service.getAllGenres()).thenReturn(Arrays.asList(russianClassic, foreignClassic));
    }

    @Test
    public void testFindAllGenres() throws Exception {
        mockMvc.perform(get("/v1/genres"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").value(russianClassic))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1]").value(foreignClassic));
    }

}
