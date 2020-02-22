package ru.otus.lib.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.controller.dto.GenreDto;
import ru.otus.lib.repository.GenreRepository;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreDao;
    
    @Override
    public List<GenreDto> getAllGenres() {
        return genreDao.findAll().stream().map(GenreDto::toDto).collect(Collectors.toList());
    }
}
