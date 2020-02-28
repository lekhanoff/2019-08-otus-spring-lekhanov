package ru.otus.lib.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.controller.dto.GenreDto;
import ru.otus.lib.repository.GenreRepository;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreDao;
    
    @HystrixCommand(fallbackMethod = "getDefaultGenres")
    @Override
    public List<GenreDto> getAllGenres() {
        return genreDao.findAll().stream().map(GenreDto::toDto).collect(Collectors.toList());
    }
    
    public List<GenreDto> getDefaultGenres() {
        return Arrays.asList(GenreDto.builder().id(Long.valueOf(-1)).name("Mystery").build());
    }

}
